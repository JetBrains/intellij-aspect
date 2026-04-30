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
import com.google.devtools.intellij.ideinfo.IdeInfo.Dependency.DependencyType
import com.intellij.aspect.private.lib.utils.isWindows
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.tests.lib.dependencyLabels
import com.intellij.aspect.testing.tests.lib.relativeArtifactPath
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AbseilTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testSourcesList() {
    val target = aspect.findTarget("//absl/algorithm:algorithm", externalRepo = "abseil-cpp")
    assertThat(target.srcsList).isEmpty()

    val cIdeInfo = target.cIdeInfo
    assertThat(cIdeInfo.ruleContext.headersList).relativeArtifactPath().containsExactly("absl/algorithm/algorithm.h")
    assertThat(cIdeInfo.compilationContext.headersList).relativeArtifactPath().contains("absl/base/config.h")
  }

  @Test
  fun testKind() {
    val target = aspect.findTarget("//absl/algorithm:algorithm", externalRepo = "abseil-cpp")
    assertThat(target.kind).isEqualTo("cc_library")
  }

  @Test
  fun testBuildFileLocation() {
    val target = aspect.findTarget("//absl/algorithm:algorithm", externalRepo = "abseil-cpp")
    val buildFile = target.buildFileArtifactLocation
    assertThat(buildFile.relativePath).isEqualTo("absl/algorithm/BUILD")
    assertThat(buildFile.isSource).isTrue()
    assertThat(buildFile.isExternal).isTrue()
  }

  @Test
  fun testDeps() {
    val target = aspect.findTarget("//absl/algorithm:algorithm", externalRepo = "abseil-cpp")
    assertThat(target.depsList)
      .dependencyLabels(DependencyType.COMPILE_TIME)
      .contains("@@abseil-cpp+//absl/base:config")
  }

  @Test
  fun testFeatures() {
    val target = aspect.findTarget("//absl/algorithm:algorithm", externalRepo = "abseil-cpp")
    assertThat(target.featuresList).containsAtLeast("header_modules", "layering_check", "parse_headers")
  }

  @Test
  fun testCopts() {
    val target = aspect.findTarget("//absl/algorithm:algorithm", externalRepo = "abseil-cpp")
    if (isWindows()) {
      assertThat(target.cIdeInfo.ruleContext.coptsList).containsAtLeast("/W3", "/wd4005", "/DNOMINMAX")
    } else {
      assertThat(target.cIdeInfo.ruleContext.coptsList).containsAtLeast("-Wall", "-Wextra", "-DNOMINMAX")
    }
  }
}
