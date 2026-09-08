/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.aspect.tools.measure

import com.google.protobuf.TextFormat
import com.intellij.aspect.lib.AspectConfig
import com.intellij.aspect.lib.Aspects
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.lib.Rules
import com.intellij.aspect.lib.deployAspectZip
import com.intellij.aspect.private.lib.utils.Logger
import com.intellij.aspect.private.lib.utils.Sandbox
import com.intellij.aspect.private.lib.utils.createTempDirectory
import com.intellij.aspect.private.lib.utils.resolvePath
import com.intellij.aspect.private.lib.utils.sandbox
import com.intellij.aspect.private.lib.utils.shutdown
import com.intellij.aspect.private.lib.utils.unzip
import com.intellij.aspect.tools.RunfilesRepo
import com.intellij.aspect.tools.lib.LanguagesArgType
import com.intellij.aspect.tools.lib.PathArgType
import com.intellij.aspect.tools.lib.RuleMapArgType
import com.intellij.aspect.tools.measure.ReportProto.Report
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.required
import kotlinx.coroutines.runBlocking
import java.io.IOError
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.system.exitProcess

// path to the bazelisk executable in the generated repository
private const val BAZELISK_EXECUTABLE = "../bazelisk/bazelisk"

// the same pinned BCR snapshot used by the test fixtures
private const val BCR_ARCHIVE = "../bcr_archive/bcr.zip"

// 4,4 = do 4 full GCs, waiting 4s between them, before recording each phase's heap
private const val STABLE_HEAP_FLAG = "--memory_profile_stable_heap_parameters=4,4"

// path to deploy the aspect to inside the project
private const val ASPECT_DESTINATION = "aspect"

// the build flag activating the deployed aspect
private val ASPECT_FLAG = "--aspects=//$ASPECT_DESTINATION/${Aspects.INTELLIJ}"

// the output groups requested for the build
private val OUTPUT_GROUPS = "--output_groups=" + OutputGroups.entries.joinToString(",") { it.groupName }

// project files that are not copied to the sandbox
private val EXCLUDED_ENTRIES = setOf(".bazeliskrc", ".bazeliskversion", "MODULE.bazel.lock")

fun main(args: Array<String>): Unit = runBlocking {
  val parser = ArgParser("measure")

  val project by parser.argument(
    PathArgType,
    description = "The project directory to measure.",
  )

  val target by parser.option(
    ArgType.String,
    shortName = "t",
    fullName = "target",
    description = "The target to build.",
  ).default("//...")

  val languages by parser.option(
    LanguagesArgType,
    shortName = "l",
    fullName = "languages",
    description = "Comma separated list of languages to deploy",
  ).required()

  val repeat by parser.option(
    ArgType.Int,
    shortName = "r",
    fullName = "repeat",
    description = "The number of times to repeat the measurement.",
  ).default(1)

  val reportFile by parser.option(
    PathArgType,
    fullName = "report",
    description = "File to write the report to instead of stdout.",
  )

  val bazelVersion by parser.option(
    ArgType.String,
    shortName = "b",
    fullName = "bazel_version",
    description = "The Bazel version to deploy the aspect for.",
  ).default("9.2.0")

  val ruleRemap by parser.option(
    RuleMapArgType,
    fullName = "rule_remap",
    description = "Comma separated ruleset repo remappings, e.g. scala=@my_scala,cc=@my_rules_cc",
  ).default(emptyMap())

  val builtin by parser.option(
    ArgType.Boolean,
    fullName = "builtin",
    description = "Deploy the aspect for builtin rules.",
  ).default(false)

  val repoCache by parser.option(
    ArgType.String,
    fullName = "repo_cache",
    description = "Persistent repository download cache directory (supports ~/).",
  )

  val nobuild by parser.option(
    ArgType.Boolean,
    fullName = "nobuild",
    description = "Execute the only the analysis phase.",
  ).default(false)

  val quiet by parser.option(
    ArgType.Boolean,
    shortName = "q",
    fullName = "quiet",
    description = "Suppress all diagnostics, and print them only if the measurement fails.",
  ).default(false)

  parser.parse(args)

  val aspect = AspectConfig(
    bazelVersion = bazelVersion,
    repoMapping = ruleRemap,
    useBuiltin = if (builtin) Rules.entries.toSet() else emptySet(),
    rulesets = languages,
  )

  val logger = if (quiet) Logger.quiet() else Logger()

  val report = catchingSandbox(aspect, logger) {
    repoCache?.let { repoCache(resolvePath(it).toAbsolutePath()) }

    linkProject(project, projectDirectory)
    deployBCRRegistry()
    deployAspectZip(projectDirectory, Path.of(ASPECT_DESTINATION), aspect)

    val report = context(Measurement(this, target, nobuild, logger)) {
      warmup()

      val baselineRun = measureRun("baseline", emptyList(), repeat)
      val aspectRun = measureRun("aspect", listOf(ASPECT_FLAG), repeat)

      Report.newBuilder()
        .setProject(project.toString())
        .setTarget(target)
        .setBazelVersion(aspect.bazelVersion)
        .setNobuild(nobuild)
        .addAllMetrics(analyze(baselineRun, aspectRun))
        .build()
    }

    shutdown()
    report
  }

  val rendered = TextFormat.printer().printToString(report)
  reportFile?.let { Files.writeString(it, rendered) } ?: print(rendered)
}

private suspend fun <T> catchingSandbox(
  aspect: AspectConfig,
  logger: Logger,
  body: suspend Sandbox.() -> T,
): T {
  try {
    return sandbox(
      bazelisk = RunfilesRepo.location(BAZELISK_EXECUTABLE),
      version = aspect.bazelVersion,
      root = createTempDirectory("measure"),
      logger = logger,
      body = body,
    )
  } catch (e: Throwable) {
    logger.error(e)
    exitProcess(2)
  }
}

/**
 * Links the project's top-level entries into the sandbox, so bazel reads the original sources
 * while new files like the deployed aspect and lock files stay in the writable sandbox.
 *
 * Entries in [EXCLUDED_ENTRIES] are skipped, the measurement dictates how bazel is launched.
 */
@Throws(IOException::class)
private fun linkProject(project: Path, destination: Path) {
  Files.newDirectoryStream(project.toAbsolutePath()).use { entries ->
    for (entry in entries) {
      val name = entry.fileName.toString()
      if (name in EXCLUDED_ENTRIES || name.startsWith("bazel-")) continue
      Files.createSymbolicLink(destination.resolve(entry.fileName), entry)
    }
  }
}

@Throws(IOError::class)
private fun Sandbox.deployBCRRegistry() {
  val registryDirectory = createDirectory("registry")
  unzip(RunfilesRepo.location(BCR_ARCHIVE), registryDirectory, stripPrefix = 1)
  registry(registryDirectory)
}

/**
 * The measurement setup for one project. All runs share the sandbox's output base, so they never
 * pay the fetch cost, but the server is restarted before every build so each repeat measures a
 * cold analysis and the repeats stay comparable.
 */
private data class Measurement(
  val sandbox: Sandbox,
  val target: String,
  val nobuild: Boolean,
  val logger: Logger,
)

/** Pre-warms the output base by fetching the project's external repositories. */
@Throws(IOException::class)
context(measurement: Measurement)
private suspend fun warmup() {
  measurement.logger.log("Warming up the Bazel server...")
  measurement.sandbox.exec("build", listOf("--nobuild", measurement.target), name = "warmup")
}

/**
 * Repeats one configuration [repeat] times and returns the raw samples per metric, in run order.
 * Repeats that did not report a metric are omitted from its samples.
 */
@Throws(IOException::class)
context(measurement: Measurement)
private suspend fun measureRun(label: String, flags: List<String>, repeat: Int): Map<String, List<Double>> {
  val samples = mutableListOf<Map<String, Double>>()
  for (i in 1..repeat) {
    measurement.logger.log("Starting measuring run $label ($i/$repeat)...")
    samples += measureOnce(flags, name = "$label ($i/$repeat)")
  }

  return METRIC_NAMES.associateWith { name -> samples.mapNotNull { it[name] } }
}

@Throws(IOException::class)
context(measurement: Measurement)
private suspend fun measureOnce(flags: List<String>, name: String): Map<String, Double> {
  val profile = measurement.sandbox.createFile("memory_profile.txt")

  val args = buildList {
    if (measurement.nobuild) add("--nobuild")
    addAll(flags)
    add("--memory_profile=$profile")
    add(STABLE_HEAP_FLAG)
    add(OUTPUT_GROUPS)
    add(measurement.target)
  }

  measurement.sandbox.shutdown()
  measurement.sandbox.exec("build", args, name)

  return parseMemoryProfile(profile) + readInfo()
}

@Throws(IOException::class)
context(measurement: Measurement)
private suspend fun readInfo(): Map<String, Double> {
  return parseInfo(measurement.sandbox.exec("info", INFO_KEYS))
}
