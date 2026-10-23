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

data class AspectConfig(
  /**
   * The Bazel version written into the config file.
   */
  val bazelVersion: String,
  /**
   * A mapping from default repo names to a specific replacement e.g., canonical repo name.
   */
  val repoMapping: Map<Rules, String>,
  /**
   * Languages for which to use the builtin rule, i.e., for which to strip rule set loads.
   */
  val useBuiltin: Set<Rules>,
  /**
   * Rule sets for which information will be requested when running the aspect. If null, take
   * all Rules mentioned in the repoMapping or useBuiltin.
   */
  val rulesets: Set<Rules>? = null,
)
