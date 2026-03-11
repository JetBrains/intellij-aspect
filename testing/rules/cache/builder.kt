/*
 * Copyright 2026 JetBrains s.r.o. and contributors.
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

package com.intellij.aspect.testing.rules.cache

import com.intellij.aspect.testing.rules.cache.BuilderProto.BuilderInput
import com.intellij.aspect.testing.rules.lib.ActionContext
import com.intellij.aspect.testing.rules.lib.ActionLibProto.AspectDeployment
import com.intellij.aspect.testing.rules.lib.action
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) = action<BuilderInput>(args) { input ->
  deployProject(input.projectArchive)
  deployRegistry(input.bcrArchive)

  val aspectPath = deployAspectMock(input.aspectModule)

  for (config in input.configsList) {
    if (config.aspectDeployment == AspectDeployment.BCR)  {
      writeModule(config.modulesList, aspectPath)
    } else {
      writeModule(config.modulesList, null)
    }

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
