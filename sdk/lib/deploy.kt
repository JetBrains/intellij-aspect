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
package com.intellij.aspect.lib

import java.io.FilterInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.zip.ZipInputStream
import kotlin.io.path.exists
import kotlin.io.path.extension
import kotlin.io.path.readText

/**
 * Location of the bundled aspect archive inside the jar.
 */
private const val BUNDLED_ASPECT = "/archive_ide.zip"

data class AspectConfig(
  /**
   * The Bazel version written into the config file.
   */
  val bazelVersion: String,
  /**
   * A mapping from default repo names to a specific replacement e.g., conical repo name.
   */
  val repoMapping: Map<Rules, String>,
  /**
   * Languages for which to use the builtin rule, i.e., for which to strip rule set loads.
   */
  val useBuiltin: Set<Rules>,
)

/**
 * Deploy an aspect archive to a workspace directory.
 *
 * Extracts all files from the archive, rewrites their load statements using the
 * provided transformers, and generates the aspect configuration. If the archiveZip
 * is null, the bundled zip inside the jar will be deployed.
 *
 * @throws IOException if extraction or file operations fail
 */
@Throws(IOException::class)
fun deployAspectZip(
  workspaceRoot: Path,
  relativeDestination: Path,
  config: AspectConfig,
  archiveZip: Path? = null,
) {
  require(!relativeDestination.isAbsolute)
  require(archiveZip == null || archiveZip.extension == "zip")

  val destination = workspaceRoot.resolve(relativeDestination)
  Files.createDirectories(destination)

  val transformers = mutableListOf(
    TransformRelativePaths(relativeDestination),
    TransformExternalRepositories(config.repoMapping),
  )

  if (Rules.CC in config.useBuiltin) {
    transformers.add(TransformCcToolchainType)
  }
  if (Rules.PYTHON in config.useBuiltin) {
    transformers.add(TransformPythonToolchainType)
  }
  if (Rules.JAVA in config.useBuiltin) {
    transformers.add(TransformJavaSemantics)
  }
  transformers.add(TransformBuiltinRules(config.useBuiltin))

  val archiveStream = if (archiveZip == null) {
    config.javaClass.getResourceAsStream(BUNDLED_ASPECT)
  } else {
    Files.newInputStream(archiveZip)
  }
  requireNotNull(archiveStream)

  extractZipArchive(destination, archiveStream, transformers)
  writeAspectConfig(destination, config)
}

@Throws(IOException::class)
private fun extractZipArchive(
  destination: Path,
  archiveZip: InputStream,
  transformers: List<Transformer>,
) {
  Files.createDirectories(destination)

  ZipInputStream(archiveZip).use { stream ->
    generateSequence { stream.nextEntry }.forEach { entry ->
      val target = destination.resolve(entry.name)

      if (entry.isDirectory) {
        Files.createDirectories(target)
      } else {
        val payload = transformFile(stream.nonClosing(), transformers)
        if (!target.exists() || target.readText() != payload) {
          Files.writeString(
            target,
            payload,
            Charsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING,
          )
        }
      }
    }
  }
}

/**
 * Returns a view of this [InputStream] whose [close] is a no-op.
 *
 * Useful when handing the stream to a consumer that closes it, while the caller
 * needs to keep the underlying stream open (e.g. iterating a [ZipInputStream]).
 */
private fun InputStream.nonClosing(): InputStream = object : FilterInputStream(this) {
  override fun close() { /* no-op */ }
}
