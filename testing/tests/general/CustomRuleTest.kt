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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CustomRuleTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testCustomBinaryIsDiscovered() {
    val target = aspect.findTarget("//:custom_bin")
    assertThat(target.kind).isEqualTo("custom_binary")
    assertThat(target.hasExecutableInfo()).isTrue()
    assertThat(target.executableInfo.hasExecutableFile()).isTrue()
  }

  @Test
  fun testCustomTestIsDiscovered() {
    val target = aspect.findTarget("//:custom_test")
    assertThat(target.kind).isEqualTo("custom_test")
    assertThat(target.hasExecutableInfo()).isTrue()
    assertThat(target.executableInfo.hasExecutableFile()).isTrue()
  }

  @Test
  fun testCustomLibraryHasNoExecutableInfo() {
    // a non-executable custom rule has no module provider, so the aspect writes no info file
    assertThat(aspect.findTargets("//:custom_lib")).isEmpty()
  }

  @Test
  fun testWeirdBinarySurvivesHostileAttributes() {
    val target = aspect.findTarget("//:weird_bin")
    assertThat(target.kind).isEqualTo("weird_binary")
    assertThat(target.hasExecutableInfo()).isTrue()
    assertThat(target.srcsList).isEmpty()
    assertThat(target.envMap).isEmpty()
    assertThat(target.envInheritList).isEmpty()
  }

  @Test
  fun testWeirdTestSurvivesHostileAttributes() {
    val target = aspect.findTarget("//:weird_test_tgt")
    assertThat(target.kind).isEqualTo("weird_test")
    assertThat(target.hasExecutableInfo()).isTrue()
    assertThat(target.srcsList).isEmpty()
    assertThat(target.hasTestInfo()).isTrue()
    assertThat(target.testInfo.size).isNotEmpty()
  }

  @Test
  fun testWeirdLibraryStillNotDiscovered() {
    assertThat(aspect.findTargets("//:weird_lib")).isEmpty()
  }

  @Test
  fun testMakeVariableExpansionInEnv() {
    val target = aspect.findTarget("//:makevar_bin")
    assertThat(target.envMap["PLAIN"]).isEqualTo("value")
    assertThat(target.envMap["LITERAL"]).isEqualTo("\$HOME")
    assertThat(target.envMap["MODE"]).isNotEmpty()
    assertThat(target.envMap["LOC"]).contains("custom_lib")
  }
}
