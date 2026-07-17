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
package com.intellij.aspect.testing.tests.scala

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GeneratedSourcesTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testGeneratedSourceIsListed() {
    val target = aspect.findTarget("//:lib")
    assertThat(target.hasScalaTargetInfo()).isTrue()

    val generated = target.srcsList.filter { it.relativePath.endsWith("Gen.scala") }
    assertThat(generated).isNotEmpty()
    assertThat(generated.all { !it.isSource }).isTrue()
  }

  @Test
  fun testGeneratedSourceIsMaterialized() {
    // Generated sources are materialized by the main aspect via the build output
    // group so the IDE can index them.
    val buildFiles = aspect.findOutputGroup(OutputGroups.BUILD)
    assertThat(buildFiles.filter { it.endsWith("Gen.scala") }).isNotEmpty()
  }

  @Test
  fun testCustomRuleWithScalaSourcesIsScalaTarget() {
    val target = aspect.findTarget("//:custom")
    assertThat(target.kind).isEqualTo("my_scala_library")
    assertThat(target.hasScalaTargetInfo()).isTrue()
    assertThat(target.srcsList.map { it.relativePath }).contains("Custom.scala")
  }
}
