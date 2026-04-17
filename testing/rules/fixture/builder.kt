package com.intellij.aspect.testing.rules.fixture

import com.google.devtools.intellij.ideinfo.IdeInfo.TargetIdeInfo
import com.google.protobuf.TextFormat
import com.intellij.aspect.private.lib.utils.parseTextProto
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
  val arguments = parseTextProto<BuilderArguments>(args[0])

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
