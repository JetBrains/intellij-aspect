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
class BuildFileTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testMainBuildFile() {
    val target = aspect.findTarget("//:main")
    assertThat(target.buildFileArtifactLocation.relativePath).isEqualTo("BUILD")
    assertThat(target.buildFileArtifactLocation.rootPath).isEmpty()
    assertThat(target.buildFileArtifactLocation.isSource).isTrue()
  }

  @Test
  fun testLibBuildFile() {
    val target = aspect.findTarget("//lib:lib")
    assertThat(target.buildFileArtifactLocation.relativePath).isEqualTo("lib/BUILD")
    assertThat(target.buildFileArtifactLocation.rootPath).isEmpty()
    assertThat(target.buildFileArtifactLocation.isSource).isTrue()
  }
}
