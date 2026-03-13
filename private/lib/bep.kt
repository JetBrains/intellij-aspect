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
package com.intellij.aspect.private.lib

import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.toPath

private val MAPPER = ObjectMapper()

/**
 * Parses a single BEP JSON event and extracts file URIs from the
 * namedSetOfFiles.files array.
 */
fun parseBepEvent(event: String): List<Path> {
  val root = MAPPER.readTree(event)
  val files = root.get("namedSetOfFiles")?.get("files") ?: return emptyList()

  return files.mapNotNull { it.get("uri")?.asText() }.map { URI(it).toPath() }
}

/**
 * Parses an entire BEP file and extracts all file URIs.
 */
@Throws(IOException::class)
fun parseBepFile(bepFile: Path): List<Path> {
  return Files.newBufferedReader(bepFile).use { reader ->
    reader.lineSequence().flatMap(::parseBepEvent).distinct().toList()
  }
}
