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
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.Dependency.DependencyType
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.assertThatDeps
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
  @Suppress("DEPRECATION")
  fun testAssociates() {
    val target = aspect.findTarget("//:A")
    assertThat(target.hasKotlinTargetInfo()).isTrue()
    assertThat(target.kind).isEqualTo("kt_jvm_library")

    // Associates reported correctly
    if (aspect.isBCRDeployment()) {
      assertThat(target.kotlinTargetInfo.associatedTargetsList.toSet())
        .containsExactly(aspect.findTarget("//:B").key, aspect.findTarget("//:C").key)
    } else {
      assertThat(target.kotlinTargetInfo.associatedTargetsList)
        .containsExactly(aspect.findTarget("//:B").key, aspect.findTarget("//:C").key)
    }

    // Dependencies reported correctly.
    assertThatDeps(target.depsList).withType(DependencyType.COMPILE_TIME).labels().containsExactly("//:B")
    assertThatDeps(target.depsList).withType(DependencyType.EXPORTED_COMPILE_TIME).isEmpty()
  }

  @Test
  fun testTransitivesPresent() {
    val targetB = aspect.findTarget("//:B")
    assertThat(targetB.hasKotlinTargetInfo()).isTrue()
    assertThat(targetB.srcsList.size).isEqualTo(1)
    assertThat(targetB.srcsList[0].isSource).isTrue()
    assertThat(targetB.srcsList[0].relativePath).isEqualTo("B.kt")
    assertThatDeps(targetB.depsList).withType(DependencyType.EXPORTED_COMPILE_TIME).labels().containsExactly("//:C")

    val targetC = aspect.findTarget("//:C")
    assertThat(targetC.hasKotlinTargetInfo()).isTrue()
    assertThat(targetC.srcsList.size).isEqualTo(1)
    assertThat(targetC.srcsList[0].isSource).isTrue()
    assertThat(targetC.srcsList[0].relativePath).isEqualTo("C.kt")

    assertThatDeps(targetC.depsList).withType(DependencyType.COMPILE_TIME).isEmpty()
    assertThatDeps(targetC.depsList).withType(DependencyType.EXPORTED_COMPILE_TIME).isEmpty()
    assertThatDeps(targetC.depsList).withType(DependencyType.RUNTIME).isEmpty()
  }
}
