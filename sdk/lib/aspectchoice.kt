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

enum class Languages {
  CC,
  PYTHON,
  JAVA,
  KOTLIN,
  SCALA,
  GO,
  PROTO,
}

val RULE_NAMES = mapOf(
  Languages.CC to "@rules_cc",
  Languages.PYTHON to "@rules_python",
  Languages.JAVA to "@rules_java",
  Languages.KOTLIN to "@rules_kotlin",
  Languages.SCALA to "@rules_scala",
  Languages.GO to "@rules_go",
  Languages.PROTO to "@protobuf",
)

// Aspects in correct (topological) order together with the languages for which they should be present.
private val aspectsWithLanguages =
  listOf(
    "modules:protobuf_info.bzl%intellij_protobuf_info_aspect" to setOf(Languages.PROTO),
    "modules:cc_info.bzl%intellij_cc_info_aspect" to setOf(Languages.CC),
    "modules:py_info.bzl%intellij_py_info_aspect" to setOf(Languages.PYTHON),
    "modules:python_info.bzl%intellij_python_info_aspect" to setOf(Languages.PYTHON),
    "modules:java_info.bzl%intellij_java_info_aspect" to setOf(Languages.JAVA),
    "modules:kotlin_info.bzl%intellij_kotlin_info_aspect" to setOf(Languages.KOTLIN),
    "modules:scala_info.bzl%intellij_scala_info_aspect" to setOf(Languages.SCALA),
    "modules:jvm_info.bzl%intellij_jvm_info_aspect" to setOf(Languages.JAVA, Languages.KOTLIN, Languages.SCALA),
    "modules:java_common_info.bzl%intellij_java_common_info_aspect" to
      setOf(Languages.JAVA, Languages.KOTLIN, Languages.SCALA),
    "modules:go_info.bzl%intellij_go_info_aspect" to setOf(Languages.GO),
    "intellij:aspect.bzl%intellij_info_aspect" to Languages.values().toSet(),
  )

// For the specified languages, return the list of aspects to be run in correct order.
fun aspectsForLanguages(languages: Set<Languages>): List<String> {
  return aspectsWithLanguages.filter { (aspect, runsOnLanguage) ->
    languages.any { it in runsOnLanguage }
  }.map { it.first }
}

// If the repository names of the rules for certain languages are known, provide the appropriate
// repo-mapping to be used in the aspect configuration.
fun repoMappingForLanguages(mapping: Map<Languages, String>): Map<String, String> =
  mapping.mapKeys { (language, _) -> RULE_NAMES[language]!! }
