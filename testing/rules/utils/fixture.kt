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
package com.intellij.aspect.testing.rules.utils

import com.google.common.truth.Truth.assertThat
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.CIdeInfo
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.PyIdeInfo
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.TargetIdeInfo
import com.google.protobuf.Message
import com.intellij.aspect.testing.rules.fixture.AspectFixture

fun AspectFixture.findCIdeInfo(
  label: String,
  externalRepo: String? = null,
  fractionalAspectIds: List<String> = emptyList(),
): CIdeInfo {
  val target = findTarget(label, externalRepo, fractionalAspectIds)
  assertThat(target.hasCIdeInfo()).isTrue()

  return target.cIdeInfo
}

fun AspectFixture.findPyIdeInfo(
  label: String,
  externalRepo: String? = null,
  fractionalAspectIds: List<String> = emptyList(),
): PyIdeInfo {
  val target = findTarget(label, externalRepo, fractionalAspectIds)
  assertThat(target.hasPyIdeInfo()).isTrue()

  return target.pyIdeInfo
}

inline fun <reified T : Message> AspectFixture.findToolchainInfo(label: String, fieldNumber: Int): T {
  val descriptor = assertNotNull(TargetIdeInfo.getDescriptor().findFieldByNumber(fieldNumber))

  val target = findTarget(label).depsList.asSequence()
    .map { findTarget(it.target.label) }
    .filter { it.hasField(descriptor) }
    .map { it.getField(descriptor) }
    .filterIsInstance<T>()
    .firstOrNull()

  return assertNotNull(target)
}
