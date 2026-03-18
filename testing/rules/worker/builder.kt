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

import com.google.devtools.intellij.ideinfo.IdeInfo.TargetIdeInfo
import com.google.protobuf.TextFormat
import com.intellij.aspect.lib.AspectConfig
import com.intellij.aspect.lib.deployAspectZip
import com.intellij.aspect.testing.rules.fixture.FixtureProto.TestFixture
import com.intellij.aspect.private.lib.utils.unzip
import com.intellij.aspect.testing.rules.fixture.FixtureProto.AspectDeployment
import com.intellij.aspect.testing.rules.fixture.FixtureProto.BazelModule
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

private val OUTPUT_GROUPS = listOf("intellij-info")

private val ASPECT_PREFIX = mapOf(
  AspectDeployment.BCR to "@intellij_aspect//",
  AspectDeployment.MATERIALIZED to "//aspect/default/",
  AspectDeployment.BUILTIN to "//aspect/builtin/",
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
        deployIdeAspect(input.aspectIdeArchive, version, useBuiltin = false)
      }

      AspectDeployment.BUILTIN -> {
        writeModules(input.config.modulesList)
        deployIdeAspect(input.aspectIdeArchive, version, useBuiltin = true)
      }

      else -> throw IllegalArgumentException("unknown aspect deployment: $deployment")
    }

    val prefix = ASPECT_PREFIX.getValue(deployment)
    val aspects = input.config.aspectsList.map { prefix + it }

    val files = bazelBuild(
      version,
      targets = input.targetsList,
      aspects = aspects,
      outputGroups = OUTPUT_GROUPS,
      profile = Path.of(input.outputProfile),
    )
    require(files.isNotEmpty()) { "no files were generated" }

    val builder = TestFixture.newBuilder()
    builder.config = input.config
    files.map(::readInfoFile).forEach(builder::addTargets)

    Files.newOutputStream(Path.of(input.outputProto)).use { outputStream ->
      builder.build().writeTo(outputStream)
    }
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

@Throws(IOException::class)
private fun Sandbox.deployBcrAspect(archive: String): Path {
  val directory = tempDirectory("aspect")
  unzip(Path.of(archive), directory)

  return directory
}

@Throws(IOException::class)
private fun Sandbox.deployIdeAspect(archive: String, bazelVersion: String, useBuiltin: Boolean) {
  val config = AspectConfig(
    bazelVersion = bazelVersion,
    repoMapping = emptyMap(),
    useBuiltin = useBuiltin,
  )

  val subdir = if (useBuiltin) "builtin" else "default"
  deployAspectZip(projectDirectory, Path.of("aspect", subdir), Path.of(archive), config)
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
      writer.appendLine("local_path_override(module_name = 'intellij_aspect', path = '$aspect')")
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

