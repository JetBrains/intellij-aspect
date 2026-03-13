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

package com.intellij.aspect.tools.deploy

import com.intellij.aspect.lib.AspectConfig
import com.intellij.aspect.lib.deployAspectZip
import com.intellij.aspect.tools.RunfilesRepo
import com.intellij.aspect.tools.lib.executeCommand
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.system.exitProcess

private const val ARCHIVE_IDE = "archive_ide.zip"
private const val LOCAL_DEPLOY = "archive_bcr.tar.gz"

fun main(args: Array<String>) {
  val parser = ArgParser("deploy")

  val method by parser.argument(
    ArgType.String,
    description = "Deployment method: bcr, materialized, or builtin"
  )

  val path by parser.argument(
    ArgType.String,
    description = "Target directory path"
  )

  val bazelExecutable by parser.option(
    ArgType.String,
    shortName = "b",
    fullName = "bazel",
    description = "Path to bazel executable"
  ).default("bazel")

  val verbose by parser.option(
    ArgType.Boolean,
    shortName = "v",
    fullName = "verbose",
    description = "Show detailed progress and stack traces"
  ).default(false)

  parser.parse(args)

  val targetPath = Path.of(path).toAbsolutePath()

  try {
    when (method) {
      "bcr" -> deployBcr(targetPath)
      "materialized" -> deployIde(targetPath, bazelExecutable, useBuiltin = false)
      "builtin" -> deployIde(targetPath, bazelExecutable, useBuiltin = true)
    }

    System.err.println("Deployed aspect ($method) to $targetPath")
  } catch (e: Exception) {
    System.err.println("Error: ${e.message}")
    if (verbose) {
      e.printStackTrace()
    }

    exitProcess(2)
  }
}

@Throws(IOException::class)
private fun deployBcr(targetPath: Path) {
  Files.createDirectories(targetPath)

  executeCommand(
    "tar",
    "xf", RunfilesRepo.rlocation(LOCAL_DEPLOY).toString(),
    "-C", targetPath.toString(),
    "--strip-components", "1",
  )
}

@Throws(IOException::class)
private fun deployIde(targetPath: Path, bazelExecutable: String, useBuiltin: Boolean) {
  val version = executeCommand(bazelExecutable, "--version").removePrefix("bazel").trim()

  val config = AspectConfig(
    bazelVersion = version,
    repoMapping = emptyMap(),
    useBuiltin = useBuiltin,
  )

  deployAspectZip(
    workspaceRoot = targetPath,
    relativeDestination = Path.of("aspect", if (useBuiltin) "builtin" else "default"),
    archiveZip = RunfilesRepo.rlocation(ARCHIVE_IDE),
    config = config
  )
}
