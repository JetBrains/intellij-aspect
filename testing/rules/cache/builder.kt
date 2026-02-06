package com.intellij.aspect.testing.rules.cache

import com.intellij.aspect.testing.rules.cache.BuilderProto.BuilderInput
import com.intellij.aspect.testing.rules.lib.ActionContext
import com.intellij.aspect.testing.rules.lib.action
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) = action<BuilderInput>(args) { input ->
  deployProject(input.projectArchive)
  deployRegistry(input.bcrArchive)

  val aspectPath = deployAspectMock(input.aspectModule)

  for (config in input.configsList) {
    writeModule(config.modulesList, aspectPath)
    bazelBuild(config.bazel, listOf("//..."), flags = listOf("--nobuild"))
  }

  archiveRepoCache(input.outputArchive)
}

@Throws(IOException::class)
private fun ActionContext.deployAspectMock(moduleFile: String): Path {
  val directory = tempDirectory("aspect")
  Files.copy(Path.of(moduleFile), directory.resolve("MODULE.bazel"))

  return directory
}
