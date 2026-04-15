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

package com.intellij.aspect.private.lib.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.toPath
import kotlin.let

private val MAPPER = ObjectMapper()

/**
 * Collect all output groups mentioned in a sequence of BEP events.
 *
 * As we only use this parser for small examples, we can afford to materialize the intermediate sets.
 */
private class OutputGroupParser() {
  private val outputGroups = mutableMapOf<String, Set<Path>>()
  private val namedSets = mutableMapOf<String, Set<Path>>()

  fun noteEvent(event: String) {
    val root = MAPPER.readTree(event)

    root.get("id", "namedSet", "id")?.let { id ->
      val files = root.get("namedSetOfFiles", "files") ?: emptyList()
      var paths = files.mapNotNull { it.get("uri")?.asText() }.map { URI(it).toPath() }.toSet()
      root.get("namedSetOfFiles", "fileSets")?.mapNotNull { it.get("id") }?.forEach {
        paths = paths union (namedSets[it.asText()] ?: emptySet())
      }
      namedSets[id.asText()] = paths
    }

    root.get("completed", "outputGroup")?.forEach { outputGroup ->
      val name = outputGroup.get("name")?.asText() ?: ""
      var entries = outputGroups[name] ?: emptySet()
      outputGroup.get("fileSets")?.mapNotNull { it.get("id") }?.forEach {
        entries = entries union (namedSets[it.asText()] ?: emptySet())
      }
      outputGroups[name] = entries
    }
  }

  fun build(): Map<String, Set<Path>> = outputGroups
}

private fun JsonNode.get(vararg path: String): JsonNode? {
  return path.fold(this) { node, element -> node.get(element) ?: return null }
}

/**
 * Parses an entire BEP file and extract output groups.
 */
@Throws(IOException::class)
fun parseBepOutputGroups(bepFile: Path): Map<String, Set<Path>> {
  val parser = OutputGroupParser()
  Files.newBufferedReader(bepFile).use { reader ->
    reader.lineSequence().forEach { parser.noteEvent(it) }
  }
  return parser.build()
}

private fun parseBepFileEvent(event: String): List<Path> {
  val root = MAPPER.readTree(event)
  val files = root.get("namedSetOfFiles")?.get("files") ?: return emptyList()
  return files.mapNotNull { it.get("uri")?.asText() }.map { URI(it).toPath() }
}

/**
 * Parses an entire BEP file and extracts all file URIs mentioned.
 */
@Throws(IOException::class)
fun parseBepFileForFiles(bepFile: Path): List<Path> {
  return Files.newBufferedReader(bepFile).use { reader ->
    reader.lineSequence().flatMap(::parseBepFileEvent).distinct().toList()
  }
}
