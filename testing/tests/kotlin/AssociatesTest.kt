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

package com.intellij.aspect.testing.tests.kotlin

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AssociatesTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testAssociates() {
    val target = aspect.findTarget("//:A")
    assertThat(target.hasKotlinTargetInfo()).isTrue()
    assertThat(target.kind).isEqualTo("kt_jvm_library")

    // Associates reported correctly
    assertThat(target.kotlinTargetInfo.associatesList).containsExactly("@@//:B", "@@//:C")
  }

  @Test
  fun testTransitivesPresent() {
    val targetB = aspect.findTarget("//:B")
    assertThat(targetB.hasKotlinTargetInfo()).isTrue()
    assertThat(targetB.srcsList.size).isEqualTo(1)
    assertThat(targetB.srcsList[0].isSource).isTrue()
    assertThat(targetB.srcsList[0].relativePath).isEqualTo("B.kt")

    val targetC = aspect.findTarget("//:C")
    assertThat(targetC.hasKotlinTargetInfo()).isTrue()
    assertThat(targetC.srcsList.size).isEqualTo(1)
    assertThat(targetC.srcsList[0].isSource).isTrue()
    assertThat(targetC.srcsList[0].relativePath).isEqualTo("C.kt")
  }
}
