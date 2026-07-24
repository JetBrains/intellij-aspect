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
package com.intellij.aspect.testing.tests.general

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Assume.assumeTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GeneralTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testTJava() {
    val target = aspect.findTarget("//:main_java")
    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.hasCIdeInfo()).isFalse()
    assertThat(target.kind).isEqualTo("java_binary")
    assertThat(target.hasExecutableInfo()).isTrue()
    assertThat(target.testonly).isFalse()
  }

  @Test
  fun testCC() {
    val target = aspect.findTarget("//:main_cc")
    assertThat(target.kind).isEqualTo("cc_binary")
    assertThat(target.hasJavaProvider()).isFalse()
    assertThat(target.hasCIdeInfo()).isTrue()
    assertThat(target.hasExecutableInfo()).isTrue()
    assertThat(target.testonly).isFalse()
  }

  @Test
  fun testCustom() {
    val target = aspect.findTarget("//:mycustom_B")
    assertThat(target.kind).isEqualTo("custom_rule")
    assertThat(target.generatorName).isEqualTo("mycustom")
    assertThat(target.hasJavaProvider()).isFalse()
    assertThat(target.hasCIdeInfo()).isFalse()
    assertThat(target.testonly).isFalse()
  }

  @Test
  fun testMetrics() {
    assertThat(aspect.getMetrics().skyframeNodeCount).isAtLeast(10) // sanity check that the metrics was recorded
    assertThat(aspect.getMetrics().skyframeNodeCount).isAtMost(35_000)
    assertThat(aspect.getMetrics().usedHeapSizeAfterGc).isAtLeast(1_000_000) // sanity check
    assertThat(aspect.getMetrics().usedHeapSizeAfterGc).isAtMost(20_000_000)
    // The following metrics are not always present, so only verify upper bounds
    assertThat(aspect.getMetrics().evaluatedConfiguredTarget).isAtMost(15_000)
  }
}
