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

package com.intellij.aspect.testing.rules.lib

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

private val EXECUTABLE_MARKER = byteArrayOf(0x45, 0x58)

/**
 * Compress a directory into a ZIP file.
 *
 * Custom ZIP to preserve a file's executbale flag when extraced with [unzip].
 */
@Throws(IOException::class)
fun zip(srcDirectory: Path, outFile: Path) {
  ZipOutputStream(Files.newOutputStream(outFile, StandardOpenOption.CREATE)).use { out ->
    Files.walk(srcDirectory).use { stream ->
      stream.filter(Files::isRegularFile).forEach { file ->
        out.putNextEntry(createEntry(srcDirectory, file))
        Files.newInputStream(file).use { it.transferTo(out) }
      }
    }
  }
}

/**
 * Extracts a ZIP file into a directory.
 *
 * Custom ZIP to preserve a file's executbale flag when compressed with [zip].
 */
@Throws(IOException::class)
fun unzip(srcFile: Path, outDirectory: Path, stripPrefix: Int = 0) {
  ZipInputStream(Files.newInputStream(srcFile)).use { src ->
    for (entry in generateSequence { src.nextEntry }) {
      if (entry.isDirectory) continue

      val path = outDirectory.resolve(Path.of(entry.name).stripPrefix(stripPrefix))
      Files.createDirectories(path.parent)
      Files.newOutputStream(path, StandardOpenOption.CREATE).use(src::transferTo)

      if (entry.isExecutable()) {
        path.toFile().setExecutable(true)
      }
    }
  }
}

private fun Path.stripPrefix(prefix: Int): Path {
  return subpath(prefix, nameCount)
}

private fun createEntry(base: Path, file: Path): ZipEntry {
  val relativePath = base.relativize(file).toString().replace('\\', '/')
  val entry = ZipEntry(relativePath)

  if (Files.isExecutable(file)) {
    entry.extra = EXECUTABLE_MARKER
  }

  return entry
}

private fun ZipEntry.isExecutable(): Boolean {
  return extra != null && extra.contentEquals(EXECUTABLE_MARKER)
}