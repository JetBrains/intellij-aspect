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

package com.intellij.aspect.testing.rules.fixture

import com.google.devtools.intellij.ideinfo.IdeInfo.TargetIdeInfo
import com.google.protobuf.TextFormat
import com.intellij.aspect.private.lib.utils.parseTextProtoResponseFile
import com.intellij.aspect.testing.rules.fixture.BuilderProto.BuilderArguments
import com.intellij.aspect.testing.rules.fixture.FixtureProto.AspectDeployment
import com.intellij.aspect.testing.rules.fixture.FixtureProto.TestFixture
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import kotlin.collections.map

private const val INTELLIJ_INFO = "intellij-info"

fun main(args: Array<String>) {
  val arguments = parseTextProtoResponseFile<BuilderArguments>(args[0])

  val builder = TestFixture.newBuilder().apply {
    addAllOutputs(arguments.outputGroupsList)

    configBuilder.apply {
      setBazelVersion(arguments.bazelVersion)
      setAspectDeployment(AspectDeployment.BCR)
    }

    arguments.outputGroupsList.first { it.name == INTELLIJ_INFO }.filesList
      .map(Path::of)
      .map(::readInfoFile)
      .forEach(::addTargets)
  }

  Files.newOutputStream(Path.of(arguments.outputProto)).use { outputStream ->
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
