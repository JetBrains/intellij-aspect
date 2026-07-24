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

private const val CC_TOOLCHAIN_FIELD = "CC_TOOLCHAIN_TYPE"
private const val PYTHON_TOOLCHAIN_FIELD = "PYTHON_TOOLCHAIN_TYPE"
private const val JAVA_SEMANTICS_FIELD = "JAVA_SEMANTICS"
private const val CC_TOOLCHAIN_LABEL = "@bazel_tools//tools/cpp:toolchain_type"
private const val PYTHON_TOOLCHAIN_LABEL = "@bazel_tools//tools/python:toolchain_type"
private const val JAVA_TOOLCHAIN_LABEL = "@bazel_tools//tools/java:toolchain_type"
private const val JAVA_TOOLCHAIN_KEY = "JAVA_RUNTIME_TOOLCHAIN_TYPE"
private const val SCALA_TOOLCHAIN_CONSTANT = "SCALA_TOOLCHAIN_TYPE"
private const val SCALA_TOOLCHAIN_VALUE = "//scala:toolchain_type"

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
class TransformExternalRepositories(private val mapping: Map<Rules, String>) : Transformer {

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
 * Replaces CC_TOOLCHAIN_TYPE load from rules_cc with a direct Label assignment. Used for replacing
 * the load from rules_cc with a direct Label assignment when the user's project uses the builtin
 * rules.
 */
object TransformCcToolchainType : Transformer {

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    val needsToolchainType = loads.removeAll { stmt ->
      stmt.repository is Repository.External && stmt.repository.name == "@rules_cc" && stmt.arguments.contains(
        CC_TOOLCHAIN_FIELD,
      )
    }

    if (needsToolchainType) {
      lines.add(0, "$CC_TOOLCHAIN_FIELD = Label(\"$CC_TOOLCHAIN_LABEL\")")
    }
  }
}

/**
 * Replaces PYTHON_TOOLCHAIN_TYPE load from rules_python with a direct Label assignment. Used for replacing
 * the load from rules_python with a direct Label assignment when the user's project uses the builtin
 * rules.
 */
object TransformPythonToolchainType : Transformer {

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    val needsToolchainType = loads.removeAll { stmt ->
      stmt.repository is Repository.External && stmt.repository.name == "@rules_python" && stmt.arguments.contains(
        PYTHON_TOOLCHAIN_FIELD,
      )
    }

    if (needsToolchainType) {
      lines.add(0, "$PYTHON_TOOLCHAIN_FIELD = Label(\"$PYTHON_TOOLCHAIN_LABEL\")")
    }
  }
}

/**
 * Replace JAVA_SEMANTICS load by an explicit struct assigning the toolchain key to a direct Label when the user's
 * project uses the builtin rules.
 */
object TransformJavaSemantics : Transformer {

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    val needsSemantics = loads.removeAll { stmt ->
      stmt.repository is Repository.External && stmt.repository.name == "@rules_java" &&
        stmt.arguments.contains(JAVA_SEMANTICS_FIELD)
    }

    if (needsSemantics) {
      lines.add(0, "$JAVA_SEMANTICS_FIELD = struct($JAVA_TOOLCHAIN_KEY = Label(\"$JAVA_TOOLCHAIN_LABEL\"))")
    }
  }
}

class TransformScalaToolchainType(val scalaRepositoryName: String) : Transformer {

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    val needsToolchain = lines.removeAll { line -> line.startsWith("$SCALA_TOOLCHAIN_CONSTANT = ") }
    if (needsToolchain) {
      lines.add(0, "$SCALA_TOOLCHAIN_CONSTANT = \"${scalaRepositoryName}$SCALA_TOOLCHAIN_VALUE\"")
    }
  }
}
