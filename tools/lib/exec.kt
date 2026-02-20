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
package com.intellij.aspect.tools.lib

import com.intellij.aspect.private.lib.parseBepFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

/**
 * Execute a command and returns stdout.
 */
@Throws(IOException::class)
fun executeCommand(vararg command: String, pwd: Path = Path.of(".")): String {
  val process = ProcessBuilder(command.toList())
    .directory(pwd.toFile())
    .start()

  val exitCode = process.waitFor()
  if (exitCode != 0) {
    process.errorStream.transferTo(System.err)
    throw IOException("Command failed: ${command.joinToString(" ")}")
  }

  return String(process.inputStream.readAllBytes(), Charsets.UTF_8)
}

/**
 * Run a Bazel build command with aspects and captures all output files via BEP.
 */
@Throws(IOException::class)
fun executeBuild(
  workspaceRoot: Path,
  bazelExecutable: String,
  outputGroups: List<String>,
  aspects: List<String>,
  targets: List<String>,
  startupFlags: List<String> = emptyList(),
): List<Path> {
  // create temp file in current workspace, deleted once the function returns
  val bepFile = Files.createTempFile(workspaceRoot, "bazel-build-", ".bep.json").toAbsolutePath()

  try {
    val cmd = listOf(
      bazelExecutable,
      *startupFlags.toTypedArray(),
      "build",
      "--aspects=" + aspects.joinToString(","),
      "--build_event_json_file=$bepFile",
      "--output_groups=" + outputGroups.joinToString(","),
      *targets.toTypedArray(),
    )

    // execute the build command in the current workspace root
    executeCommand(*cmd.toTypedArray(), pwd = workspaceRoot)

    if (!Files.exists(bepFile)) {
      throw IOException("BEP file was not created")
    }

    return parseBepFile(bepFile)
  } finally {
    Files.delete(bepFile)
  }
}
