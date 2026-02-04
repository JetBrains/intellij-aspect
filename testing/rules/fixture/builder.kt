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
import com.intellij.aspect.testing.rules.fixture.BuilderProto.BuilderInput
import com.intellij.aspect.testing.rules.fixture.BuilderProto.BuilderOutput
import com.intellij.aspect.testing.rules.lib.ActionContext
import com.intellij.aspect.testing.rules.lib.action
import com.intellij.aspect.testing.rules.lib.unzip
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

private val OUTPUT_GROUPS = listOf("intellij-info")

fun main(args: Array<String>) = action<BuilderInput>(args) { input ->
  deployProject(input.projectArchive)
  deployRepoCache(input.cacheArchive)

  val aspectPath = deployAspect(input.aspectArchive)

  writeModule {
    for (module in input.config.modulesList) {
      appendLine("bazel_dep(name = '${module.name}', version = '${module.version}')")
    }

    appendLine("bazel_dep(name = 'intellij_aspect')")
    appendLine("local_path_override(module_name = 'intellij_aspect', path = '$aspectPath')")
  }

  val files = bazelBuild(
    bazel = input.config.bazel,
    targets = input.targetsList,
    aspects = input.config.aspectsList,
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
private fun ActionContext.deployAspect(archive: String): Path {
  val directory = tempDirectory("aspect")
  unzip(Path.of(archive), directory)

  return directory
}