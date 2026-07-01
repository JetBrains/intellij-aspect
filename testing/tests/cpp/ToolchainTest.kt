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
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.*
import com.intellij.aspect.private.lib.utils.isMacOS
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.findToolchainInfo
import org.junit.Assume.assumeTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ToolchainTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  private val info: CToolchainIdeInfo
    get() = aspect.findToolchainInfo<CToolchainIdeInfo>("//:main", TargetIdeInfo.C_TOOLCHAIN_IDE_INFO_FIELD_NUMBER)

  @Test
  fun testHasToolchain() {
    assertThat(info.cCompiler).isNotEmpty()
    assertThat(info.cCompiler).isEqualTo(info.cppCompiler)
  }

  @Test
  fun testHasCompilerName() {
    assertThat(info.compilerName).isNotEmpty()
  }

  @Test
  fun testHasBuiltInIncludeDirectories() {
    assertThat(info.builtInIncludeDirectoryList).isNotEmpty()
  }

  @Test
  fun testHasCompileOptions() {
    assertThat(info.cOptionList).isNotEmpty()
    assertThat(info.cppOptionList).isNotEmpty()
  }

  @Test
  fun testHasXcodeInfo() {
    assumeTrue(aspect.bazelVersion(min = 8))
    assumeTrue(isMacOS())

    val info = aspect.findToolchainInfo<XcodeIdeInfo>("//:main", TargetIdeInfo.XCODE_IDE_INFO_FIELD_NUMBER)
    assertThat(info.xcodeVersion).isNotEmpty()
    assertThat(info.macosSdkVersion).isNotEmpty()
  }
}
