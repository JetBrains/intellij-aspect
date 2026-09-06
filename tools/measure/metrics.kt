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

package com.intellij.aspect.tools.measure

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

// the bazel info keys to collect, mapped to their metric names
internal val INFO_KEYS = listOf(
  "gc-count",
  "gc-time",
  "max-heap-size",
  "peak-heap-size",
  "used-heap-size",
  "used-heap-size-after-gc",
)

internal val METRIC_NAMES = listOf(
  "analysis_heap_used",
  "analysis_heap_committed",
) + INFO_KEYS

// the memory profile phases holding the retained live set after analysis; bazel fuses analysis and
// execution into one phase when the build actions run, so the phase is named after both
private val PROFILE_PHASES = setOf(
  "Load and analyze dependencies",
  "Load, analyze dependencies and build artifacts",
)
private const val PROFILE_REGION = "heap"

private val NUMBER_REGEX = Regex("""-?\d+(?:\.\d+)?""")

/**
 * Parses `bazel info` output into metric values, stripping units like ms or MB.
 */
internal fun parseInfo(output: String): Map<String, Double> {
  val values = mutableMapOf<String, Double>()

  for (line in output.lineSequence()) {
    val name = line.substringBefore(':', "").trim()
    val number = NUMBER_REGEX.find(line.substringAfter(':'))?.value?.toDoubleOrNull() ?: continue
    values[name] = number
  }

  return values
}

/**
 * Parses a `--memory_profile` file of `<phase>:<region>:<metric>:<bytes>` lines, keeping the
 * analysis phase heap values in MB. Note that bazel spells the committed metric `commited`.
 *
 * Only one of the [PROFILE_PHASES] is present in a profile, whichever one the build ran.
 */
@Throws(IOException::class)
internal fun parseMemoryProfile(path: Path): Map<String, Double> {
  val wanted = mapOf(
    "used" to "analysis_heap_used",
    "commited" to "analysis_heap_committed",
  )

  val values = mutableMapOf<String, Double>()

  for (line in Files.readAllLines(path)) {
    val parts = line.trim().split(":")
    if (parts.size < 4) continue

    // the phase name may contain colons, the last three parts are region, metric and value
    val phase = parts.dropLast(3).joinToString(":")
    val (region, metric, raw) = parts.takeLast(3)
    if (phase !in PROFILE_PHASES || region != PROFILE_REGION) continue

    val name = wanted[metric] ?: continue
    raw.toLongOrNull()?.let { values[name] = it.toDouble() / 1024 / 1024 }
  }

  return values
}
