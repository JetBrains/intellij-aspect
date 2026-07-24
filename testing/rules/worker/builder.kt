/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.aspect.testing.rules.worker

import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.TargetIdeInfo
import com.google.protobuf.TextFormat
import com.intellij.aspect.lib.AspectConfig
import com.intellij.aspect.lib.Aspects
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.lib.Rules
import com.intellij.aspect.lib.deployAspectZip
import com.intellij.aspect.private.lib.utils.asBazelPath
import com.intellij.aspect.private.lib.utils.unzip
import com.intellij.aspect.testing.rules.fixture.FixtureProto
import com.intellij.aspect.testing.rules.fixture.FixtureProto.AspectDeployment
import com.intellij.aspect.testing.rules.fixture.FixtureProto.BazelModule
import com.intellij.aspect.testing.rules.fixture.FixtureProto.OutputGroup
import com.intellij.aspect.testing.rules.fixture.FixtureProto.RuleSet
import com.intellij.aspect.testing.rules.fixture.FixtureProto.TestFixture
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

private val ASPECT_PREFIX = mapOf(
  AspectDeployment.BCR to "@intellij_aspect//",
  AspectDeployment.MATERIALIZED to "//aspect/default/",
  AspectDeployment.BUILTIN to "//aspect/builtin/",
)

private val RULES = mapOf(
  RuleSet.CC to Rules.CC,
  RuleSet.PYTHON to Rules.PYTHON,
  RuleSet.JAVA to Rules.JAVA,
  RuleSet.KOTLIN to Rules.KOTLIN,
  RuleSet.SCALA to Rules.SCALA,
  RuleSet.GO to Rules.GO,
  RuleSet.PROTO to Rules.PROTO,
  RuleSet.LEGACY_RULES_PROTO to Rules.LEGACY_RULES_PROTO,
)

fun main(args: Array<String>) {
  require(args.contains("--persistent_worker"))

  worker(args) { input ->
    val version = input.config.bazelVersion
    deployProject(input.projectArchive)

    val deployment = input.config.aspectDeployment
    when (deployment) {
      AspectDeployment.BCR -> {
        val aspectBcrPath = deployBcrAspect(input.aspectBcrArchive)
        writeModules(input.config.modulesList, aspectBcrPath)
      }

      AspectDeployment.MATERIALIZED -> {
        writeModules(input.config.modulesList)
        deployIdeAspect(version, useBuiltin = false)
      }

      AspectDeployment.BUILTIN -> {
        writeModules(input.config.modulesList)
        deployIdeAspect(version, useBuiltin = true)
      }

      else -> throw IllegalArgumentException("unknown aspect deployment: $deployment")
    }

    val ruleSets = input.config.ruleSetsList.map { RULES[it]!! }.toSet()
    val aspects = Aspects.forRules(ruleSets)
    val prefix = ASPECT_PREFIX.getValue(deployment)
    val aspectLabels = aspects.map { prefix + it.toString() }

    val buildResult = bazelBuild(
      version,
      targets = input.targetsList,
      aspects = aspectLabels,
      outputGroups = listOf(OutputGroups.INFO.groupName) + input.outputGroupsList,
      profile = Path.of(input.outputProfile),
      execLog = Path.of(input.outputExecLog),
      flags = input.extraFlagsList,
    )
    val files = buildResult.outputGroups
    require(files.isNotEmpty()) { "no files were generated" }

    val builder = TestFixture.newBuilder().apply {
      config = input.config

      files[OutputGroups.INFO.groupName]?.map(::readInfoFile)?.forEach(::addTargets)
      files.entries.map(::createOutputGroup).forEach(::addOutputs)

      addAllExtraFlags(input.extraFlagsList)

      metrics = FixtureProto.Metrics.newBuilder().apply {
        usedHeapSizeAfterGc = parseSize(buildResult.infoHeap)
        buildResult.metrics?.get("buildGraphMetrics")?.get("postInvocationSkyframeNodeCount")?.let {
          skyframeNodeCount = it.asLong()
        }
        buildResult.metrics?.get("buildGraphMetrics")?.get("evaluatedValues")?.let {
          it.filter { it.get("skyfunctionName").asText() == "ARTIFACT_NESTED_SET" }.firstOrNull()?.let {
            evaluatedArtifactNestedSet = it.get("count").asText().toLong()
          }
          it.filter { it.get("skyfunctionName").asText() == "CONFIGURED_TARGET" }.firstOrNull()?.let {
            evaluatedConfiguredTarget = it.get("count").asText().toLong()
          }
        }
      }.build()
    }

    Files.newOutputStream(Path.of(input.outputProto)).use { outputStream ->
      builder.build().writeTo(outputStream)
    }
  }
}

@Throws(NumberFormatException::class)
private fun parseSize(sizeString: String): Long {
  // Heap size is "helpfully" reported with SI suffixes and rounded, so we have reverse that encoding (to the extend
  // possible).
  // https://github.com/bazelbuild/bazel/blob/deaa7a9352d6a4ebd0e8e644b82a26332f36329f/src/main/java/com/google/devtools/build/lib/util/StringUtilities.java#L96
  val sizeSI = sizeString.substringBefore("B")
  return when {
    sizeSI.endsWith("K") -> sizeSI.substringBefore("K").toLong() * 1000
    sizeSI.endsWith("M") -> sizeSI.substringBefore("M").toLong() * 1000_000
    sizeSI.endsWith("G") -> sizeSI.substringBefore("G").toLong() * 1000_000_000
    else -> sizeString.toLong()
  }
}

@Throws(IOException::class)
private fun readInfoFile(path: Path): TargetIdeInfo {
  Files.newInputStream(path).use { input ->
    val builder = TargetIdeInfo.newBuilder()
    TextFormat.Parser.newBuilder().build().merge(InputStreamReader(input, StandardCharsets.UTF_8), builder)

    return builder.build()
  }
}

private fun Sandbox.createOutputGroup(entry: Map.Entry<String, Set<Path>>): OutputGroup {
  return OutputGroup.newBuilder().apply {
    name = entry.key
    addAllFiles(entry.value.map(::relativeToOutputBase))
  }.build()
}

@Throws(IOException::class)
private fun Sandbox.deployBcrAspect(archive: String): Path {
  val directory = tempDirectory("aspect")
  unzip(Path.of(archive), directory)

  return directory
}

@Throws(IOException::class)
private fun Sandbox.deployIdeAspect(bazelVersion: String, useBuiltin: Boolean) {
  val config = AspectConfig(
    bazelVersion = bazelVersion,
    repoMapping = emptyMap(),
    useBuiltin = if (useBuiltin) Rules.entries.toSet() else emptySet(),
  )

  val subdir = if (useBuiltin) "builtin" else "default"
  deployAspectZip(projectDirectory, Path.of("aspect", subdir), config)
}

@Throws(IOException::class)
fun Sandbox.writeModules(modules: List<BazelModule>, aspect: Path? = null) {
  Files.newOutputStream(
    projectDirectory.resolve("MODULE.bazel"),
    StandardOpenOption.CREATE,
    StandardOpenOption.TRUNCATE_EXISTING,
  ).bufferedWriter().use { writer ->
    for (module in modules) {
      writer.appendLine("bazel_dep(name = '${module.name}', version = '${module.version}')")
    }

    for (module in modules) {
      if (module.config.isNotEmpty()) {
        writer.appendLine(module.config.trim())
      }
    }

    if (aspect != null) {
      writer.appendLine("bazel_dep(name = 'intellij_aspect')")
      writer.appendLine("local_path_override(module_name = 'intellij_aspect', path = '${asBazelPath(aspect)}')")
    }
  }

  Files.newOutputStream(
    projectDirectory.resolve(".bazelrc"),
    StandardOpenOption.CREATE,
    StandardOpenOption.TRUNCATE_EXISTING,
  ).bufferedWriter().use { writer ->
    for (module in modules) {
      for (line in module.flagsList) {
        writer.appendLine(line.trim())
      }
    }
  }
}
