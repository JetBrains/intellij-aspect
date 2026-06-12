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
class ExecConfigTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testToolHasTwoConfigurations() {
    val targets = aspect.findTargets("//:exec_tool")
    assertThat(targets).hasSize(2)
  }

  @Test
  fun testConfigurationIsAlwaysPopulated() {
    val targets = aspect.findTargets("//:exec_tool") + aspect.findTargets("//:exec_main")
    assertThat(targets).isNotEmpty()

    for (target in targets) {
      assertThat(target.key.configuration).isNotEmpty()
    }
  }

  @Test
  fun testDefaultAndExecConfigurationsDiffer() {
    val configurations = aspect.findTargets("//:exec_tool").map { it.key.configuration }.toSet()
    assertThat(configurations).hasSize(2)
  }
}
