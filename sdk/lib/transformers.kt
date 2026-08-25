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

import com.intellij.aspect.private.lib.utils.asBazelPath
import java.nio.file.Path

/**
 * Rewrites repo-absolute load paths by prepending the deploy directory prefix.
 */
class TransformRelativePaths(private val prefix: Path) : Transformer {

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    loads.replaceAll { stmt ->
      if (stmt.repository != Repository.Absolute) {
        stmt
      } else {
        stmt.copy(path = "${asBazelPath(prefix)}/${stmt.path}")
      }
    }
  }
}

/**
 *  Remaps external repository names according to the provided mapping.
 */
class TransformExternalRepositories(mapping: Map<Rules, String>) : Transformer {

  val nameMapping = mapping.mapKeys { (language, _) -> language.rulesetName }

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    loads.replaceAll { stmt ->
      if (stmt.repository !is Repository.External || stmt.repository.name !in nameMapping) {
        stmt
      } else {
        stmt.copy(repository = Repository.External(nameMapping[stmt.repository.name]!!))
      }
    }
  }
}

/**
 * Removes load statements from external repositories not in the allowed list. Used for removing
 * loads when the user's project uses the builtin rules.
 */
class TransformBuiltinRules(useBuiltin: Set<Rules>) : Transformer {

  val repoNamesToRemove = useBuiltin.map { it.rulesetName }

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    loads.removeAll { stmt ->
      stmt.repository is Repository.External && stmt.repository.name in repoNamesToRemove
    }
  }
}

/**
 * Replaces a loaded field with a top-level variable for builtin deployment.
 */
abstract class TransformFieldLoad(private val fieldName: String, private val replacement: String) : Transformer {

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    val needsField = loads.removeAll { stmt ->
      stmt.repository is Repository.External && stmt.arguments.contains(fieldName)
    }

    if (needsField) {
      lines.add(0, "$fieldName = $replacement")
    }
  }
}

object TransformCcToolchainType : TransformFieldLoad(
  fieldName = "CC_TOOLCHAIN_TYPE",
  replacement = label("@bazel_tools//tools/cpp:toolchain_type"),
)

object TransformPythonToolchainType : TransformFieldLoad(
  fieldName = "PYTHON_TOOLCHAIN_TYPE",
  replacement = label("@bazel_tools//tools/python:toolchain_type"),
)

object TransformJavaSemantics : TransformFieldLoad(
  fieldName = "JAVA_SEMANTICS",
  replacement = "struct(JAVA_TOOLCHAIN_TYPE = ${label("@bazel_tools//tools/jdk:toolchain_type")})",
)

class TransformScalaToolchainType(scalaRepositoryName: String) : TransformFieldLoad(
  fieldName = "SCALA_TOOLCHAIN_TYPE",
  replacement = label("$scalaRepositoryName//scala:toolchain_type"),
)

private fun label(value: String): String = "Label(\"$value\")"
