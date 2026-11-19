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
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.CToolchainIdeInfo
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.TargetIdeInfo
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.assertThatOutputGroup
import com.intellij.aspect.testing.rules.utils.findToolchainInfo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GeneratedToolchainTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  private val info: CToolchainIdeInfo
    get() = aspect.findToolchainInfo<CToolchainIdeInfo>("//:main", TargetIdeInfo.C_TOOLCHAIN_IDE_INFO_FIELD_NUMBER)

  @Test
  fun testToolchainIsCustom() {
    assertThat(info.cCompiler).endsWith("/custom_cc")
    assertThat(info.cppCompiler).isEqualTo(info.cCompiler)
    assertThat(info.compilerName).isEqualTo("custom_wrapped")
    assertThat(info.builtInIncludeDirectoryList).containsExactly("/usr/include", "/usr/lib")
  }

  @Test
  fun testCompilerIsBuilt() {
    val ccToolsFiles = aspect.findOutputGroup(OutputGroups.CC_TOOLS)
    assertThatOutputGroup(ccToolsFiles).containsFile("custom_cc")
  }
}
