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
import com.intellij.aspect.testing.rules.utils.assertNotNull
import com.intellij.aspect.testing.rules.utils.assertThatArtifacts
import com.intellij.aspect.testing.rules.utils.assertThatOutputGroup
import com.intellij.aspect.testing.rules.utils.execrootPath
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

  @Test
  fun testAllFiles() {
    assertThatArtifacts(info.allFilesList).relativePaths().containsExactly(
      "toolchain/bin/qcc",
      "toolchain/include/qnx.h",
      "toolchain/include/generated_config.h",
    )
  }

  @Test
  fun testAllFilesDistinguishSourceAndGenerated() {
    val artifacts = info.allFilesList.associateBy { it.relativePath }

    val source = assertNotNull(artifacts["toolchain/bin/qcc"])
    assertThat(source.isSource).isTrue()
    assertThat(source.isExternal).isFalse()
    assertThat(source.rootPath).isEmpty()
    assertThat(source.execrootPath()).isEqualTo("toolchain/bin/qcc")

    val generated = assertNotNull(artifacts["toolchain/include/generated_config.h"])
    assertThat(generated.isSource).isFalse()
    assertThat(generated.rootPath).isNotEmpty()
    assertThat(generated.execrootPath()).endsWith("/toolchain/include/generated_config.h")
  }

  @Test
  fun testAllFilesInOutputGroups() {
    val sync = aspect.findOutputGroup(OutputGroups.SYNC)
    assertThatOutputGroup(sync).containsFile("toolchain/bin/qcc")
    assertThatOutputGroup(sync).containsFile("toolchain/include/qnx.h")
    assertThatOutputGroup(sync).doesNotContainFile("toolchain/include/generated_config.h")

    val build = aspect.findOutputGroup(OutputGroups.BUILD)
    assertThatOutputGroup(build).containsFile("toolchain/include/generated_config.h")
    assertThatOutputGroup(build).doesNotContainFile("toolchain/bin/qcc")
    assertThatOutputGroup(build).doesNotContainFile("toolchain/include/qnx.h")
  }
}
