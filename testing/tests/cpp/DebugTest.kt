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
package com.intellij.aspect.testing.tests.cpp

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.private.lib.utils.isMacOS
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.assertThatArtifacts
import com.intellij.aspect.testing.rules.utils.startsWith
import org.junit.Assume.assumeTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DebugTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testMainDebugInfoMacOS() {
    assumeTrue(isMacOS())

    val target = aspect.findTarget("//:main")

    val linkAction = target.ccDebugInfo.linkActionsList.single()
    assertThat(linkAction.argumentsList).startsWith().contains("-Wl,-oso_prefix,")
    assertThatArtifacts(linkAction.outputsList).relativePaths().contains("main")

    val compileAction = target.ccDebugInfo.compileActionsList.single()
    assertThat(compileAction.argumentsList).contains("-g")
    assertThat(compileAction.argumentsList).contains("-fdebug-prefix-map=__BAZEL_EXECUTION_ROOT__=.")
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("_objs/main/main.o")
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("_objs/main/main.d")
  }

  @Test
  fun testLibDebugInfoMacOS() {
    assumeTrue(isMacOS())

    val target = aspect.findTarget("//lib:lib")
    assertThat(target.ccDebugInfo.linkActionsList).isEmpty()

    val compileAction = target.ccDebugInfo.compileActionsList.single()
    assertThat(compileAction.argumentsList).contains("-g")
    assertThat(compileAction.argumentsList).contains("-fdebug-prefix-map=__BAZEL_EXECUTION_ROOT__=.")
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("lib/_objs/lib/source.o")
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("lib/_objs/lib/source.d")
  }
}
