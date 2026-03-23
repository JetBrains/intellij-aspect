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

import java.nio.file.Path

private val ALLOWED_BUILTIN_LOADS = listOf("@bazel_tools")

private const val CC_TOOLCHAIN_FIELD = "CC_TOOLCHAIN_TYPE"
private const val CC_TOOLCHAIN_LABEL = "@bazel_tools//tools/cpp:toolchain_type"

/**
 * Rewrites repo-absolute load paths by prepending the deploy directory prefix.
 */
class TransformRelativePaths(private val prefix: Path) : Transformer {

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    loads.replaceAll { stmt ->
      if (stmt.repository != Repository.Absolute) {
        stmt
      } else {
        stmt.copy(path = prefix.resolve(stmt.path).toString())
      }
    }
  }
}

/**
 *  Remaps external repository names according to the provided mapping.
 */
class TransformExternalRepositories(private val mapping: Map<String, String>) : Transformer {

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    loads.replaceAll { stmt ->
      if (stmt.repository !is Repository.External || stmt.repository.name !in mapping) {
        stmt
      } else {
        stmt.copy(repository = Repository.External(mapping[stmt.repository.name]!!))
      }
    }
  }
}

/**
 * Removes load statements from external repositories not in the allowed list. Used for removing
 * loads when the user's project uses the builtin rules.
 */
object TransformBuiltinRules : Transformer {

  override fun apply(loads: MutableList<LoadStatement>, lines: MutableList<String>) {
    loads.removeAll { stmt ->
      stmt.repository is Repository.External && stmt.repository.name !in ALLOWED_BUILTIN_LOADS
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
