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
package com.intellij.aspect.testing.tests.python

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests that a custom rule providing PyInfo is recognized as a Python target,
 * even though its srcs attribute holds the generator's inputs rather than
 * Python sources.
 */
@RunWith(JUnit4::class)
class CustomRuleTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testCustomRuleIsPythonTarget() {
    val target = aspect.findTarget("//:gen")
    assertThat(target.kind).isEqualTo("py_codegen")
    assertThat(target.hasPythonTargetInfo()).isTrue()
  }

  @Test
  fun testGeneratedSourcesAreReported() {
    val target = aspect.findTarget("//:gen")
    val generated = target.pythonTargetInfo.generatedSourcesList
    assertThat(generated.filter { it.relativePath.endsWith("gen.py") }).isNotEmpty()
  }

  @Test
  fun testGeneratedSourcesAreMaterializedDuringSync() {
    val syncFiles = aspect.findOutputGroup(OutputGroups.SYNC)
    assertThat(syncFiles.filter { it.endsWith("gen.py") }).isNotEmpty()
  }
}
