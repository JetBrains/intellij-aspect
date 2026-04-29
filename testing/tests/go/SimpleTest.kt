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
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.tests.lib.relativeArtifactPath
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
    assertThat(target.srcsList).relativeArtifactPath().containsExactly("bin.go")

    assertThat(target.goTargetInfo.sdkHomePath.relativePath).containsMatch("bin/go(|.exe)$")
  }

  @Test
  fun testLibrary() {
    val target = aspect.findTarget("//:library")
    assertThat(target.kind).isEqualTo("go_library")
    assertThat(target.srcsList).relativeArtifactPath().containsExactly("lib.go")

    assertThat(target.goTargetInfo.sdkHomePath.relativePath).containsMatch("bin/go(|.exe)$")
  }

  @Test
  fun testTest() {
    val target = aspect.findTarget("//:test")
    assertThat(target.kind).isEqualTo("go_test")
    assertThat(target.srcsList).relativeArtifactPath().containsExactly("test.go")

    assertThat(target.goTargetInfo.sdkHomePath.relativePath).containsMatch("bin/go(|.exe)$")
  }
}
