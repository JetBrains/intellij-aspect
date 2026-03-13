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

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.google.protobuf.Message
import com.google.protobuf.TextFormat

/**
 * JSON output format for comparison results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ComparisonReport(
  @get:JsonProperty("additional_targets") val additionalTargets: List<String>,
  @get:JsonProperty("missing_targets") val missingTargets: List<String>,
  val differences: List<DifferenceEntry>
)

/**
 * Single difference entry in JSON output.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DifferenceEntry(
  val target: String,
  val path: String,
  val type: String,
  val expected: String?,
  val actual: String?
)

/**
 * Converts a Comparison result to a JSON-friendly report structure.
 */
fun Comparison.toJsonReport(): ComparisonReport {
  val differenceEntries = differences.flatMap { (target, diffs) ->
    diffs.map { it.toJsonEntry(target) }
  }

  return ComparisonReport(
    missingTargets = missing.map { it.key.label },
    additionalTargets = additional.map { it.key.label },
    differences = differenceEntries
  )
}

private fun Difference.toJsonEntry(targetLabel: String): DifferenceEntry {
  val typeString = when (type) {
    DifferenceType.MISSING_ELEMENT -> "missing"
    DifferenceType.ADDITIONAL_ELEMENT -> "new"
    DifferenceType.VALUE_MISMATCH -> "different"
  }

  return DifferenceEntry(
    target = targetLabel,
    path = path.toString(),
    type = typeString,
    expected = expected?.let { formatValueForJson(it) },
    actual = actual?.let { formatValueForJson(it) }
  )
}

private fun formatValueForJson(value: Any): String {
  return when (value) {
    is Message -> TextFormat.printer().printToString(value).trim()
    else -> value.toString()
  }
}

fun serializeToJson(report: ComparisonReport): String {
  val mapper = ObjectMapper()
  mapper.enable(SerializationFeature.INDENT_OUTPUT)
  return mapper.writeValueAsString(report)
}
