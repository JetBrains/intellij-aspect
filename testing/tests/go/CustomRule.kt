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
import com.intellij.aspect.testing.rules.utils.dependencyLabels
import com.intellij.aspect.testing.rules.utils.relativeArtifactPath
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CustomRule {
  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testMain() {
    val target = aspect.findTarget("//:main")
    assertThat(target.kind).isEqualTo("go_library")
    assertThat(target.goTargetInfo.sourcesList).relativeArtifactPath().containsExactly("main.go")
    assertThat(target.depsList).dependencyLabels(DependencyType.COMPILE_TIME).containsExactly("//:generated_lib")
  }

  @Test
  fun testGeneratedLib() {
    val target = aspect.findTarget("//:generated_lib")
    assertThat(target.kind).isEqualTo("simple_gen")
    assertThat(target.goTargetInfo.sourcesList).relativeArtifactPath()
      .containsExactly("generated_lib_/generated_lib.go")
  }
}
