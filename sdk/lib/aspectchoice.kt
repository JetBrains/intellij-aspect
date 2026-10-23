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

// If the repository names of the rules for certain languages are known, provide the appropriate
// repo-mapping to be used in the aspect configuration.
fun repoMappingForRules(mapping: Map<Rules, String>): Map<String, String> {
  return mapping.mapKeys { (language, _) -> language.rulesetName }
}

/**
 * The default aspect run during the sync.
 */
val INTELLIJ_ASPECT: AspectDefinition by lazy { AspectDefinition.fromResource("/config/aspect_ide.textproto") }

/**
 * The debug aspect for C and C++ targets. Reports the compile and link command lines and collects
 * the files the debugger reads in the `intellij-debug` output group. One build must not run this
 * aspect together with [INTELLIJ_ASPECT].
 */
val INTELLIJ_CC_DEBUG_ASPECT: AspectDefinition by lazy { AspectDefinition.fromResource("/config/cc_debug_aspect.textproto") }

@Deprecated(
  message = "use the location of the bundled aspect definition",
  replaceWith = ReplaceWith("INTELLIJ_ASPECT.location.toString()"),
)
val ASPECT_NAME: String = INTELLIJ_ASPECT.location.toString()
