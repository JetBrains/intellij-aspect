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
package com.intellij.aspect.testing.tests.go

import com.google.common.truth.Truth.assertThat
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.ActionInfo
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.CompilationMode
import com.intellij.aspect.testing.rules.fixture.AspectFixture
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
  fun testCompilationMode() {
    // the fixture builds with --compilation_mode=dbg
    for (label in listOf("//:main", "//:test", "//lib:lib")) {
      assertThat(aspect.findTarget(label).goDebugInfo.compilationMode).isEqualTo(CompilationMode.DBG)
    }
  }

  @Test
  fun testMainDebugInfo() {
    val info = aspect.findTarget("//:main").goDebugInfo

    val compileAction = info.compileActionsList.single()
    assertThat(compileAction.mnemonic).isEqualTo("GoCompilePkg")
    assertThat(compileAction.gcFlags()).containsAtLeast("-N", "-l")

    val linkAction = info.linkActionsList.single()
    assertThat(linkAction.mnemonic).isEqualTo("GoLink")
    assertThat(linkAction.argumentsList).containsNoneOf("-s", "-w")
  }

  @Test
  fun testLibDebugInfo() {
    val info = aspect.findTarget("//lib:lib").goDebugInfo

    assertThat(info.compileActionsList.single().gcFlags()).containsAtLeast("-N", "-l")
    assertThat(info.linkActionsList).isEmpty()
  }

  @Test
  fun testTestDebugInfo() {
    val info = aspect.findTarget("//:test").goDebugInfo

    assertThat(info.compileActionsList).isNotEmpty()
    assertThat(info.linkActionsList).hasSize(1)
  }
}

// rules_go passes all gc flags as one argument after `-gcflags`
private fun ActionInfo.gcFlags(): List<String> {
  val index = argumentsList.indexOf("-gcflags")
  if (index < 0 || index + 1 >= argumentsCount) return emptyList()
  return argumentsList[index + 1].split(' ').filter { it.isNotEmpty() }
}
