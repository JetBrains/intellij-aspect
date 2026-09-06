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
package com.intellij.aspect.tools.lib

import com.intellij.aspect.lib.Rules
import kotlinx.cli.ArgType
import java.nio.file.Path

object PathArgType : ArgType<Path>(hasParameter = true) {
  override val description = "{ Path }"

  override fun convert(value: kotlin.String, name: kotlin.String): Path = Path.of(value)
}

/** Parses a comma-separated language list into rulesets, matched case-insensitively. */
object LanguagesArgType : ArgType<Set<Rules>>(hasParameter = true) {
  override val description = "{ language[,language]* }"

  override fun convert(value: kotlin.String, name: kotlin.String): Set<Rules> {
    return value.split(",").filter { it.isNotBlank() }.map { language ->
      Rules.entries.firstOrNull { it.name.equals(language.trim(), ignoreCase = true) }
        ?: throw IllegalArgumentException("unknown language '$language'")
    }.toSet()
  }
}

/** Parses a comma-separated list of `ruleset=@repo` re-mappings into a [Rules] keyed map. */
object RuleMapArgType : ArgType<Map<Rules, String>>(hasParameter = true) {
  override val description = "{ ruleset=@repo[,ruleset=@repo]* }"

  override fun convert(value: kotlin.String, name: kotlin.String): Map<Rules, kotlin.String> {
    return value.split(",").filter { it.isNotBlank() }.associate { entry ->
      val (key, value) = entry.split("=")

      val rule = Rules.entries.firstOrNull { it.name.equals(key, ignoreCase = true) }
        ?: throw IllegalArgumentException("unknown ruleset '$key' in rule remapping")

      rule to value
    }
  }
}

