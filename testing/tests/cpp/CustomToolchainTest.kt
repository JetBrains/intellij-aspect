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
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.findToolchainInfo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CustomToolchainTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  private val info: CToolchainIdeInfo
    get() = aspect.findToolchainInfo("//:main", TargetIdeInfo.C_TOOLCHAIN_IDE_INFO_FIELD_NUMBER)

  @Test
  fun testCapturesToolchainEnvironment() {
    assertThat(info.cEnvironmentMap).containsEntry("QNX_HOST", "/proc/self/cwd/external/qnx_sdp/host/linux/x86_64")
    assertThat(info.cEnvironmentMap).containsEntry("QNX_TARGET", "/proc/self/cwd/external/qnx_sdp/target/qnx")

    assertThat(info.cppEnvironmentMap).containsEntry("QNX_HOST", "/proc/self/cwd/external/qnx_sdp/host/linux/x86_64")
    assertThat(info.cppEnvironmentMap).containsEntry("QNX_TARGET", "/proc/self/cwd/external/qnx_sdp/target/qnx")
  }

  @Test
  fun testCompilerName() {
    assertThat(info.compilerName).isEqualTo("sdp-qcc")
  }

  @Test
  fun testTargetName() {
    assertThat(info.targetName).isEqualTo("sdp-qnx")
  }

  @Test
  fun testCompilerPaths() {
    assertThat(info.cCompiler).isEqualTo("/usr/bin/false")
    assertThat(info.cppCompiler).isEqualTo("/usr/bin/false")
  }

  @Test
  fun testSysroot() {
    assertThat(info.sysroot).isEqualTo("/proc/self/cwd/external/qnx_sdp/target/qnx")
  }

  @Test
  fun testBuiltInIncludeDirectories() {
    assertThat(info.builtInIncludeDirectoryList).contains("/proc/self/cwd/external/qnx_sdp/target/qnx/usr/include")
  }

  @Test
  fun testCompileOptionsAreLanguageSpecific() {
    assertThat(info.cOptionList).contains("-std=gnu11")
    assertThat(info.cOptionList).doesNotContain("-std=c++17")

    assertThat(info.cppOptionList).contains("-std=c++17")
    assertThat(info.cppOptionList).doesNotContain("-std=gnu11")
  }
}
