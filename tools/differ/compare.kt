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

import com.google.devtools.intellij.ideinfo.IdeInfo.TargetIdeInfo
import com.google.devtools.intellij.ideinfo.IdeInfo.TargetKey
import com.google.protobuf.Descriptors
import com.google.protobuf.Message
import com.google.protobuf.TextFormat
import java.nio.file.Path

enum class DifferenceType {
  MISSING_ELEMENT,
  ADDITIONAL_ELEMENT,
  VALUE_MISMATCH,
}

/**
 * Represents a difference between two protobuf messages.
 * Path is built in reverse as we return from recursion.
 */
data class Difference(
  val path: Path,
  val type: DifferenceType,
  val expected: String?,
  val actual: String?,
)

private fun valueToString(value: Any): String {
  return when (value) {
    is Message -> TextFormat.printer().printToString(value)
    else -> value.toString()
  }
}

private fun difference(type: DifferenceType, actual: Any? = null, expected: Any? = null): Difference {
  return Difference(
    path = Path.of(""),
    type = type,
    actual = actual?.let(::valueToString),
    expected = expected?.let(::valueToString),
  )
}

private fun updatePaths(prefix: String, differences: List<Difference>): List<Difference> {
  return differences.map {
    it.copy(path = Path.of(prefix).resolve(it.path))
  }
}

/**
 * Uses reflection to access the static getDescriptor() method for generic
 * protobuf introspection.
 */
private fun Message.getDescriptor(): Descriptors.Descriptor {
  return requireNotNull(javaClass.getMethod("getDescriptor").invoke(null) as? Descriptors.Descriptor)
}

private fun compare(legacy: Any, current: Any): List<Difference> {
  require(legacy.javaClass == current.javaClass)

  return when (legacy) {
    is TargetKey -> compareField(legacy, current as TargetKey, "label")
    is Message -> compareMessage(legacy, current as Message)
    else -> compareDefault(legacy, current)
  }
}

private fun areEqual(legacy: Any, current: Any): Boolean = compare(legacy, current).isEmpty()

private fun compareField(legacy: Message, current: Message, name: String): List<Difference> {
  return compareField(legacy, current, legacy.getDescriptor().findFieldByName(name))
}

/**
 * Bidirectional list comparison: checks that every legacy item exists in
 * current and vice versa. Collects ALL missing and additional items.
 */
private fun compareList(legacy: List<*>, current: List<*>): List<Difference> {
  val legacyItems = legacy.filterNotNull()
  val currentItems = current.filterNotNull()
  val diffs = mutableListOf<Difference>()

  for (legacyItem in legacyItems) {
    if (currentItems.none { areEqual(legacyItem, it) }) {
      diffs.add(difference(DifferenceType.MISSING_ELEMENT, expected = legacyItem))
    }
  }

  for (currentItem in currentItems) {
    if (legacyItems.none { areEqual(it, currentItem) }) {
      diffs.add(difference(DifferenceType.ADDITIONAL_ELEMENT, actual = currentItem))
    }
  }

  return diffs
}

/**
 * Compares a single protobuf field: uses list comparison for repeated fields,
 * direct comparison otherwise.
 */
private fun compareField(legacy: Message, current: Message, descriptor: Descriptors.FieldDescriptor): List<Difference> {
  val diffs = if (!descriptor.isRepeated) {
    compare(legacy.getField(descriptor), current.getField(descriptor))
  } else {
    compareList(legacy.getField(descriptor) as List<*>, current.getField(descriptor) as List<*>)
  }

  return updatePaths(descriptor.name, diffs)
}

private fun compareMessage(legacy: Message, current: Message): List<Difference> {
  return legacy.getDescriptor().fields.flatMap {
    compareField(legacy, current, it)
  }
}

private fun compareDefault(legacy: Any, current: Any): List<Difference> {
  return if (legacy == current) {
    emptyList()
  } else {
    listOf(difference(DifferenceType.VALUE_MISMATCH, expected = legacy, actual = current))
  }
}

data class Comparison(
  val differences: Map<String, List<Difference>>,
  val missing: List<TargetIdeInfo>,
  val additional: List<TargetIdeInfo>,
)

fun compareTargets(legacy: List<TargetIdeInfo>, current: List<TargetIdeInfo>): Comparison {
  val legacyByKey = legacy.associateBy { it.key }
  val currentByKey = current.associateBy { it.key }

  val commonKeys = legacyByKey.keys.intersect(currentByKey.keys)

  val differences = mutableMapOf<String, List<Difference>>()

  for (key in commonKeys) {
    val diffs = compare(legacyByKey[key]!!, currentByKey[key]!!)

    if (diffs.isNotEmpty()) {
      differences[key.toString()] = diffs
    }
  }

  return Comparison(
    differences = differences,
    missing = (legacyByKey.keys - currentByKey.keys).map { legacyByKey[it]!! },
    additional = (currentByKey.keys - legacyByKey.keys).map { currentByKey[it]!! },
  )
}
