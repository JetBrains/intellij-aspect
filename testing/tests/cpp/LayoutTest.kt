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
import com.google.devtools.intellij.ideinfo.IdeInfo.CIdeInfo
import com.google.devtools.intellij.ideinfo.IdeInfo.TargetIdeInfo
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.tests.lib.relativeArtifactPath
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LayoutTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testMainSource() {
    val info = aspect.findTarget("//main:main")
    assertThat(info.srcsList).relativeArtifactPath().contains("main/main.cc")
  }

  @Test
  fun testMainGeneratedHeader() {
    val info = aspect.findCIdeInfo("//lib:lib")
    assertThat(info.ruleContext.headersList).relativeArtifactPath().contains("lib/generated.h")
  }

  @Test
  fun testExternalSource() {
    val info = aspect.findTarget("//srcs:lib", externalRepo = "external_module")
    assertThat(info.srcsList).relativeArtifactPath().contains("srcs/lib.cc")
  }

  @Test
  fun testExternalHeader() {
    val info = aspect.findCIdeInfo("//srcs:lib", externalRepo = "external_module")
    assertThat(info.ruleContext.headersList).relativeArtifactPath().contains("srcs/lib.h")
  }

  @Test
  fun testBuildFileLocations() {
    assertThat(aspect.findTarget("//main:main").buildFileArtifactLocation.relativePath)
      .isEqualTo("main/BUILD")
    assertThat(aspect.findTarget("//lib:lib").buildFileArtifactLocation.relativePath)
      .isEqualTo("lib/BUILD")
    assertThat(aspect.findTarget("//srcs:lib", externalRepo = "external_module").buildFileArtifactLocation.relativePath)
      .isEqualTo("srcs/BUILD")
  }
}
