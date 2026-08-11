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

enum class Rules(val rulesetName: String, val group: ModuleGroups? /* TODO: remove nullable */) {
  CC("@rules_cc", ModuleGroups.CC),
  PYTHON("@rules_python", null),
  JAVA("@rules_java", null),
  KOTLIN("@rules_kotlin", null),
  SCALA("@rules_scala", null),
  GO("@rules_go", null),
  PROTO("@protobuf", null),
  LEGACY_RULES_PROTO("@rules_proto", null),
}

enum class OutputGroups(val groupName: String) {
  INFO("intellij-info"),
  SYNC("intellij-sync"),
  BUILD("intellij-build"),
}

private val DEFAULT_MODULE_GROUP = ModuleGroups.DEFAULT

/**
 * The package holding the aspect configurations, relative to the root of the deployed aspect.
 */
const val ASPECT_CONFIG_FILE = "config:aspect.bzl"

/**
 * For the specified rule sets, returns the module groups the aspect has to run. Rule sets without
 * a module contribute nothing.
 */
fun moduleGroupsForRules(rules: Set<Rules>): Set<ModuleGroups> {
  return setOf(DEFAULT_MODULE_GROUP) + rules.mapNotNull { it.group }.toSet()
}
