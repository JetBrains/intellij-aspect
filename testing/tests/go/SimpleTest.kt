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
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.Dependency.DependencyType
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.assertThatArtifacts
import com.intellij.aspect.testing.rules.utils.assertThatDeps
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SimpleTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testMain() {
    val target = aspect.findTarget("//:binary")
    assertThat(target.kind).isEqualTo("go_binary")
    assertThatArtifacts(target.srcsList).relativePaths().containsExactly("bin.go")

    assertThat(target.goTargetInfo.sdkHomePath.relativePath).containsMatch("bin/go(|.exe)$")
  }

  @Test
  fun testLibrary() {
    val target = aspect.findTarget("//:library")
    assertThat(target.kind).isEqualTo("go_library")
    assertThatArtifacts(target.goTargetInfo.sourcesList).relativePaths().containsExactly("lib.go")
    assertThat(target.goTargetInfo.sdkHomePath.relativePath).containsMatch("bin/go(|.exe)$")
  }

  @Test
  fun testTest() {
    val target = aspect.findTarget("//:test")
    assertThat(target.kind).isEqualTo("go_test")
    assertThatArtifacts(target.goTargetInfo.sourcesList).relativePaths().containsExactly("test.go")
    assertThat(target.goTargetInfo.sdkHomePath.relativePath).containsMatch("bin/go(|.exe)$")
  }

  @Test
  fun testA() {
    val target = aspect.findTarget("//testa:testa")
    assertThat(target.kind).isEqualTo("go_library")
    assertThatDeps(target.depsList).withType(DependencyType.COMPILE_TIME).labels().contains("//testa:srcs")
    assertThat(target.goTargetInfo.embedList).contains(aspect.findTarget("//testa:srcs").key)
    // Don't roll up sources, that's handled by the Bazel plugin
    assertThatArtifacts(target.goTargetInfo.sourcesList).relativePaths().containsExactly("testa/testa.go")
  }

  @Test
  fun testSrcs() {
    val target = aspect.findTarget("//testa:srcs")
    assertThat(target.kind).isEqualTo("go_source")
    assertThatArtifacts(target.goTargetInfo.sourcesList).relativePaths().containsExactly("testa/src.go", "testa/gen.go")
  }
}
