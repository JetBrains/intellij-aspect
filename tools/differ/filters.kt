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
package com.intellij.aspect.tools.differ

import java.nio.file.Path

/**
 * Filter that can suppress differences.
 * Simple predicate that takes a difference and returns true if it should be filtered out.
 */
typealias DifferenceFilter = (Difference) -> Boolean

/**
 * Predefined filters for known differences between the new aspect and the CLwB aspect.
 * Add new exception patterns here.
 */
object DefaultFilters {

  /**
   * Suppresses additional TOOLCHAIN dependencies.
   * These are infrastructure dependencies that vary between aspect implementations
   * but don't indicate actual build graph differences.
   */
  val ADDITIONAL_TOOLCHAIN: DifferenceFilter = { diff ->
    diff.path.endsWith("deps") &&
      diff.type == DifferenceType.ADDITIONAL_ELEMENT &&
      diff.actual?.contains("dependency_type: TOOLCHAIN") ?: false
  }

  /**
   * Build file names are prefix with / in the CLwB aspect.
   */
  val BUILD_FILE_NAME: DifferenceFilter = { diff ->
    diff.path.endsWith("relative_path") &&
      diff.type == DifferenceType.VALUE_MISMATCH &&
      diff.expected?.equals("/BUILD") ?: false
  }

  /**
   * Filter to ignore a set of path prefixes (i.e., message subtrees) that are not
   * provided by the current reference aspect. To ensure that those messages really
   * are new, we enforce that the value read from the reference aspect is a default value.
   */
  fun newFields(pathPrefixString: String): DifferenceFilter {
    val prefixStrings = pathPrefixString.split(",")
    val pathPrefixes = prefixStrings.map {
      it.split("/").fold(Path.of("")) { acc, fragment -> acc.resolve(fragment) }
    }
    return { diff ->
      pathPrefixes.any { diff.path.startsWith(it) } && (diff.expected in setOf("", "false"))
    }
  }

  /**
   * Filter to ignore a set of path prefixes (i.e., message subtrees) unconditionally.
   * This can be useful for fields where the semantics changed deliberately (e.g., by expanding
   * shell variables).
   */
  fun ignoreFields(pathPrefixString: String): DifferenceFilter {
    val prefixStrings = pathPrefixString.split(",")
    val pathPrefixes = prefixStrings.map {
      it.split("/").fold(Path.of("")) { acc, fragment -> acc.resolve(fragment) }
    }
    return { diff ->
      pathPrefixes.any { diff.path.startsWith(it) }
    }
  }

  /**
   * All active filters. Add new filters to this list to enable them.
   */
  val ALL = listOf(
    ADDITIONAL_TOOLCHAIN,
    BUILD_FILE_NAME,
  )
}

/**
 * Applies filters to differences and separates them into kept vs filtered.
 * Each target's diff list is partitioned individually; targets with all diffs filtered are dropped.
 */
fun filterDifferences(
  differences: Map<String, List<Difference>>,
  filters: List<DifferenceFilter>,
): FilterResult {
  val kept = mutableMapOf<String, List<Difference>>()
  val filtered = mutableMapOf<String, List<Difference>>()

  differences.forEach { (label, diffs) ->
    val (filteredDiffs, keptDiffs) = diffs.partition { diff -> filters.any { it(diff) } }

    if (keptDiffs.isNotEmpty()) {
      kept[label] = keptDiffs
    }
    if (filteredDiffs.isNotEmpty()) {
      filtered[label] = filteredDiffs
    }
  }

  return FilterResult(kept, filtered)
}

data class FilterResult(
  val kept: Map<String, List<Difference>>,
  val filtered: Map<String, List<Difference>>,
)
