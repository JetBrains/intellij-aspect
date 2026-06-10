/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.aspect.lib

enum class Rules(val rulesetName: String) {
  CC("@rules_cc"),
  PYTHON("@rules_python"),
  JAVA("@rules_java"),
  KOTLIN("@rules_kotlin"),
  SCALA("@rules_scala"),
  GO("@rules_go"),
  PROTO("@protobuf"),
}

/**
 * Aspects in correct (topological) order together with the languages for which they should be present.
 */
enum class Aspects(val pkg: String, val file: String, val aspect: String, private val rules: Set<Rules>) {
  PROTOBUF(
    "modules", "protobuf_info.bzl", "intellij_protobuf_info_aspect",
    setOf(Rules.PROTO),
  ),
  CC(
    "modules", "cc_info.bzl", "intellij_cc_info_aspect",
    setOf(Rules.CC),
  ),
  PY(
    "modules", "py_info.bzl", "intellij_py_info_aspect",
    setOf(Rules.PYTHON),
  ),
  PYTHON(
    "modules", "python_info.bzl", "intellij_python_info_aspect",
    setOf(Rules.PYTHON),
  ),
  JAVA(
    "modules", "java_info.bzl", "intellij_java_info_aspect",
    setOf(Rules.JAVA),
  ),
  KOTLIN(
    "modules", "kotlin_info.bzl", "intellij_kotlin_info_aspect",
    setOf(Rules.KOTLIN),
  ),
  SCALA(
    "modules", "scala_info.bzl", "intellij_scala_info_aspect",
    setOf(Rules.SCALA),
  ),
  JVM(
    "modules", "jvm_info.bzl", "intellij_jvm_info_aspect",
    setOf(Rules.JAVA, Rules.KOTLIN, Rules.SCALA),
  ),
  JAVA_COMMON(
    "modules", "java_common_info.bzl", "intellij_java_common_info_aspect",
    setOf(Rules.JAVA, Rules.KOTLIN, Rules.SCALA),
  ),
  GO(
    "modules", "go_info.bzl", "intellij_go_info_aspect",
    setOf(Rules.GO),
  ),
  INTELLIJ(
    "intellij", "aspect.bzl", "intellij_info_aspect",
    Rules.entries.toSet(),
  ),
  ;

  override fun toString(): String {
    return "$pkg:$file%$aspect"
  }

  companion object {

    /**
     * For the specified rulesets, returns the list of aspects to be run in the correct order.
     */
    @JvmStatic
    fun forRules(languages: Set<Rules>): List<Aspects> {
      return entries.filter { aspect -> languages.any { it in aspect.rules } || aspect == INTELLIJ }
    }
  }
}

// If the repository names of the rules for certain languages are known, provide the appropriate
// repo-mapping to be used in the aspect configuration.
fun repoMappingForRules(mapping: Map<Rules, String>): Map<String, String> {
  return mapping.mapKeys { (language, _) -> language.rulesetName }
}
