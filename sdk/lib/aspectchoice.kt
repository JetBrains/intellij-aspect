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
  LEGACY_RULES_PROTO("@rules_proto"),
}

enum class OutputGroups(val groupName: String) {
  INFO("intellij-info"),
  SYNC("intellij-sync"),
  BUILD("intellij-build"),
}

fun modulesForRules(rules: Iterable<Rules>): List<Modules> {
  val rulesets = rules.toSet()
  return Modules.entries.filter { module -> module.rulesets.isEmpty() || module.rulesets.any { it in rulesets } }
}

// If the repository names of the rules for certain languages are known, provide the appropriate
// repo-mapping to be used in the aspect configuration.
fun repoMappingForRules(mapping: Map<Rules, String>): Map<String, String> {
  return mapping.mapKeys { (language, _) -> language.rulesetName }
}

enum class Aspects(val pkg: String, val file: String, val aspect: String) {
  INTELLIJ("config", "aspect.bzl", "intellij_aspect"),
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
      return listOf(INTELLIJ)
    }
  }
}
