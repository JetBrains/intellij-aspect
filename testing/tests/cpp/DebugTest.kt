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
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.private.lib.utils.isLinux
import com.intellij.aspect.private.lib.utils.isMacOS
import com.intellij.aspect.private.lib.utils.isWindows
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.assertThatArtifacts
import com.intellij.aspect.testing.rules.utils.endsWith
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
  fun testOutputGroupMacOS() {
    assumeTrue(isMacOS())

    assertThat(aspect.findOutputGroup(OutputGroups.DEBUG)).endsWith().containsExactly(
      "_objs/main/main.o",
      "lib/_objs/lib/source.o",
      "lib/liblib.a",
      "params/_objs/params/params.o",
      "params/libparams.a",
      "_objs/test/test.o",
    )
  }

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

  @Test
  fun testOutputGroupLinux() {
    assumeTrue(isLinux())

    assertThat(aspect.findOutputGroup(OutputGroups.DEBUG)).endsWith().containsExactly(
      "_objs/test/test.pic.o",
      "test.dwp",
      "_objs/main/main.pic.o",
      "main.dwp",
      "lib/_objs/lib/source.pic.o",
      "lib/liblib.a",
      "params/_objs/params/params.pic.o",
      "params/libparams.a",
    )
  }

  @Test
  fun testMainDebugInfoLinux() {
    assumeTrue(isLinux())

    val target = aspect.findTarget("//:main")

    val linkAction = target.ccDebugInfo.linkActionsList.single()
    assertThatArtifacts(linkAction.outputsList).relativePaths().contains("main")

    val compileAction = target.ccDebugInfo.compileActionsList.single()
    assertThat(compileAction.argumentsList).contains("-g")
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("_objs/main/main.pic.o")
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("_objs/main/main.pic.d")

    // build when fission=yes
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("_objs/main/main.pic.dwo")
  }

  @Test
  fun testLibDebugInfoLinux() {
    assumeTrue(isLinux())

    val target = aspect.findTarget("//lib:lib")

    val linkAction = target.ccDebugInfo.linkActionsList.single()
    assertThatArtifacts(linkAction.outputsList).relativePaths().contains("lib/liblib.so")

    val compileAction = target.ccDebugInfo.compileActionsList.single()
    assertThat(compileAction.argumentsList).contains("-g")
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("lib/_objs/lib/source.pic.o")
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("lib/_objs/lib/source.pic.d")

    // build when fission=yes
    assertThatArtifacts(compileAction.outputsList).relativePaths().contains("lib/_objs/lib/source.pic.dwo")
  }

  @Test
  fun testOutputGroupWindows() {
    assumeTrue(isWindows())

    assertThat(aspect.findOutputGroup(OutputGroups.DEBUG)).endsWith().containsExactly(
      "_objs/main/main.obj",
      "main.pdb",
      "lib/_objs/lib/source.obj",
      "lib/lib.lib",
      "params/_objs/params/params.obj",
      "params/params.lib",
      "_objs/test/test.obj",
      "test.pdb",
    )
  }

  @Test
  fun testMainDebugInfoWindows() {
    assumeTrue(isWindows())

    val target = aspect.findTarget("//:main")

    val linkAction = target.ccDebugInfo.linkActionsList.single()
    assertThat(linkAction.argumentsList).contains("/DEBUG:FULL")
    assertThatArtifacts(linkAction.outputsList).relativePaths().contains("main.exe")
    assertThatArtifacts(linkAction.outputsList).relativePaths().contains("main.pdb")

    val compileAction = target.ccDebugInfo.compileActionsList.single()
    assertThat(compileAction.argumentsList).contains("/Z7")
    assertThatArtifacts(compileAction.outputsList).relativePaths().containsExactly("_objs/main/main.obj")
  }

  @Test
  fun testLibDebugInfoWindows() {
    assumeTrue(isWindows())

    val target = aspect.findTarget("//lib:lib")

    // the dll is linked with /DEBUG:FULL, but its pdb is not a declared output
    val linkAction = target.ccDebugInfo.linkActionsList.single()
    assertThat(linkAction.argumentsList).contains("/DLL")
    assertThatArtifacts(linkAction.outputsList).relativePaths().contains("lib/lib.if.lib")

    val compileAction = target.ccDebugInfo.compileActionsList.single()
    assertThat(compileAction.argumentsList).contains("/Z7")
    assertThatArtifacts(compileAction.outputsList).relativePaths().containsExactly("lib/_objs/lib/source.obj")
  }

  @Test
  fun testParamsFileWindows() {
    assumeTrue(isWindows())

    val target = aspect.findTarget("//params:params")

    // cl.exe is executed with a params file, but argv already contains its content
    val compileAction = target.ccDebugInfo.compileActionsList.single()
    assertThat(compileAction.argumentsList).contains("-DPARAMS_3999")
    assertThat(compileAction.argumentsList.filter { it.startsWith("@") }).isEmpty()
  }
}
