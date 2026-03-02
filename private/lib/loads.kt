/*
 * Copyright 2026 The Bazel Authors. All rights reserved.
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
import java.io.InputStream

private val LOAD_STATEMENT_RX = Regex("""^load\("([^"]+)",([^)]+)\)$""")

private val REPO_NAME_RX = Regex("""^(@[^/]+)//(.+)$""")

@Throws(IOException::class)
fun parseLoads(input: InputStream): List<LoadStatement> {
  return input.reader().useLines { lines ->
    lines.mapNotNull(::loadStatementParse).toList()
  }
}

@Throws(IOException::class)
fun transformFile(input: InputStream, transformers: List<Transformer>): String {
  val loads = mutableListOf<LoadStatement>()
  val lines = mutableListOf<String>()

  input.reader().useLines {
    it.forEach { line ->
      when (val stmt = loadStatementParse(line)) {
        null -> lines.add(line)
        else -> loads.add(stmt)
      }
    }
  }

  transformers.forEach { it.apply(loads, lines) }

  return buildString {
    loads.joinTo(this, separator = "\n", transform = ::loadStatementWrite)
    append("\n")
    lines.joinTo(this, separator = "\n")
  }
}

interface Transformer {
  fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>)
}

data class LoadStatement(
  val repository: Repository,
  val path: String,
  val arguments: String,
)

sealed interface Repository {
  object Relative : Repository

  object Absolute : Repository

  data class External(val name: String) : Repository
}

@Throws(IOException::class)
private fun loadStatementParse(line: String): LoadStatement? {
  val stmtMatch = LOAD_STATEMENT_RX.matchEntire(line) ?: return null
  val module = stmtMatch.groupValues[1]
  val arguments = stmtMatch.groupValues[2].trim()

  if (module.startsWith(':')) {
    return LoadStatement(Repository.Relative, module, arguments)
  }

  if (module.startsWith("//")) {
    return LoadStatement(Repository.Absolute, module.substring(2), arguments)
  }

  val moduleMatch = REPO_NAME_RX.matchEntire(module)
    ?: throw IOException("invalid module identifier: $module")

  return LoadStatement(
    repository = Repository.External(moduleMatch.groupValues[1]),
    path = moduleMatch.groupValues[2],
    arguments = arguments,
  )
}

private fun loadStatementWrite(stmt: LoadStatement): String {
  val repository = when (stmt.repository) {
    is Repository.Absolute -> "//"
    is Repository.Relative -> ""
    is Repository.External -> stmt.repository.name + "//"
  }

  return "load(\"$repository${stmt.path}\", ${stmt.arguments})"
}
