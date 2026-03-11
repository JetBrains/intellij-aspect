/*
 * Copyright 2026 JetBrains s.r.o. and contributors.
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

/**
 * Filter that can suppress differences.
 * Simple predicate that takes a difference and returns true if it should be filtered out.
 */
typealias DifferenceFilter = (Difference) -> Boolean

/**
 * Predefined filters for common benign differences.
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
   * All active filters. Add new filters to this list to enable them.
   */
  val ALL = listOf(
    ADDITIONAL_TOOLCHAIN
  )
}

/**
 * Applies filters to differences and separates them into kept vs filtered.
 * Each target's diff list is partitioned individually; targets with all diffs filtered are dropped.
 */
fun filterDifferences(
  differences: Map<String, List<Difference>>,
  filters: List<DifferenceFilter>
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
  val filtered: Map<String, List<Difference>>
)
