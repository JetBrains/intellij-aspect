/*
 * Copyright 2025 The Bazel Authors. All rights reserved.
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
package com.intellij.aspect.testing.rules.fixture

import com.google.devtools.intellij.ideinfo.IdeInfo.TargetIdeInfo
import com.google.protobuf.TextFormat
import com.intellij.aspect.lib.AspectConfig
import com.intellij.aspect.lib.deployAspectZip
import com.intellij.aspect.testing.rules.fixture.BuilderProto.BuilderInput
import com.intellij.aspect.testing.rules.fixture.BuilderProto.BuilderOutput
import com.intellij.aspect.testing.rules.lib.ActionContext
import com.intellij.aspect.testing.rules.lib.ActionLibProto.AspectDeployment
import com.intellij.aspect.testing.rules.lib.action
import com.intellij.aspect.testing.rules.lib.unzip
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

private val OUTPUT_GROUPS = listOf("intellij-info")

private val ASPECT_PREFIX = mapOf(
  AspectDeployment.BCR to "@intellij_aspect//",
  AspectDeployment.MATERIALIZED to "//aspect/default/",
  AspectDeployment.BUILTIN to "//aspect/builtin/",
)

fun main(args: Array<String>) = action<BuilderInput>(args) { input ->
  deployProject(input.projectArchive)
  deployRepoCache(input.cacheArchive)
  deployRegistry(input.bcrArchive)

  val deployment = input.config.aspectDeployment
  when (deployment) {
    AspectDeployment.BCR -> {
      val aspectBcrPath = deployBcrAspect(input.aspectBcrArchive)
      writeModule(input.config.modulesList, aspectBcrPath)
    }
    AspectDeployment.MATERIALIZED -> {
      writeModule(input.config.modulesList)
      deployIdeAspect(input.aspectIdeArchive, input.config.bazel.version, useBuiltin = false)
    }
    AspectDeployment.BUILTIN -> {
      writeModule(input.config.modulesList)
      deployIdeAspect(input.aspectIdeArchive, input.config.bazel.version, useBuiltin = true)
    }
    else -> throw IllegalArgumentException("unknown aspect deployment: $deployment")
  }

  val prefix = ASPECT_PREFIX.getValue(deployment)
  val aspects = input.config.aspectsList.map { prefix + it }

  val files = bazelBuild(
    bazel = input.config.bazel,
    targets = input.targetsList,
    aspects = aspects,
    outputGroups = OUTPUT_GROUPS,
  )
  require(files.isNotEmpty()) { "no files were generated" }

  val builder = BuilderOutput.newBuilder()
  builder.config = input.config
  files.map(::readInfoFile).forEach(builder::addTargets)

  Files.newOutputStream(Path.of(input.outputProto)).use { outputStream ->
    builder.build().writeTo(outputStream)
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
private fun ActionContext.deployBcrAspect(archive: String): Path {
  val directory = tempDirectory("aspect")
  unzip(Path.of(archive), directory)

  return directory
}

@Throws(IOException::class)
private fun ActionContext.deployIdeAspect(archive: String, bazelVersion: String, useBuiltin: Boolean) {
  val config = AspectConfig(
    bazelVersion = bazelVersion,
    repoMapping = emptyMap(),
    useBuiltin = useBuiltin,
  )

  val subdir = if (useBuiltin) "builtin" else "default"
  deployAspectZip(projectDirectory, Path.of("aspect", subdir), Path.of(archive), config)
}
