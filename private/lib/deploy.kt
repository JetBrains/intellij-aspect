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
package com.intellij.aspect.lib

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.zip.ZipFile
import kotlin.io.path.extension

data class AspectConfig(
  /**
   * The Bazel version written into the config file.
   */
  val bazelVersion: String,

  /**
   * A mapping from default repo names to a specific replacement e.g., conical repo name.
   */
  val repoMapping: Map<String, String>,

  /**
   * Whether to use builtin rules i.e. whether to strip rule set loads.
   */
  val useBuiltin: Boolean,
)

/**
 * Deploy an aspect archive to a workspace directory.
 *
 * Extracts all files from the archive, rewrites their load statements using the
 * provided transformers, and generates the aspect configuration.
 *
 * @throws IOException if extraction or file operations fail
 */
@Throws(IOException::class)
fun deployAspectZip(
  workspaceRoot: Path,
  relativeDestination: Path,
  archiveZip: Path,
  config: AspectConfig,
) {
  require(!relativeDestination.isAbsolute)
  require(archiveZip.extension == "zip")

  val destination = workspaceRoot.resolve(relativeDestination)
  Files.createDirectories(destination)

  val transformers = mutableListOf(
    TransformRelativePaths(relativeDestination),
    TransformExternalRepositories(config.repoMapping),
  )

  if (config.useBuiltin) {
    transformers.add(TransformCcToolchainType)
    transformers.add(TransformBuiltinRules)
  }

  extractZipArchive(destination, archiveZip, transformers)
  writeAspectConfig(destination, config)
}

@Throws(IOException::class)
private fun extractZipArchive(
  destination: Path,
  archiveZip: Path,
  transformers: List<Transformer>,
) {
  Files.createDirectories(destination)

  ZipFile(archiveZip.toFile()).use { zip ->
    zip.stream().forEach { entry ->
      val target = destination.resolve(entry.name)

      if (entry.isDirectory) {
        Files.createDirectories(target)
      } else {
        Files.writeString(
          target,
          transformFile(zip.getInputStream(entry), transformers),
          Charsets.UTF_8,
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING,
        )
      }
    }
  }
}
