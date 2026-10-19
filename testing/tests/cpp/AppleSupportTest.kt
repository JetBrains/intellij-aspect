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
import com.google.protobuf.TextFormat
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.assertThatOutputGroup
import com.intellij.aspect.testing.rules.utils.findGeneratedFile
import com.intellij.aspect.testing.rules.utils.findToolchain
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AppleSupportTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  private val target: TargetIdeInfo
    get() = aspect.findToolchain("//:main", TargetIdeInfo.C_TOOLCHAIN_IDE_INFO_FIELD_NUMBER)

  @Test
  fun testUsesRulesBasedToolchain() {
    assertThat(target.key.label).endsWith("//toolchain:toolchain")
  }

  @Test
  fun testHasXcodeInfo() {
    val info = target.cToolchainIdeInfo
    assertThat(info.hasXcodeInfo()).isTrue()
    assertThat(info.xcodeInfo.relativePath).contains("toolchain.intellij-generated-")
  }

  @Test
  fun testXcodeInfoInBuildOutputGroup() {
    val build = aspect.findOutputGroup(OutputGroups.BUILD)
    assertThatOutputGroup(build).containsFile(target.cToolchainIdeInfo.xcodeInfo.relativePath)
  }

  @Test
  fun testXcodeInfoIsResolved() {
    val file = aspect.findGeneratedFile(target.cToolchainIdeInfo.xcodeInfo)
    val info = TextFormat.parse(file.toStringUtf8(), CToolchainIdeInfo.XcodeInfo::class.java)

    assertThat(info.developerDir).startsWith("/")
    assertThat(info.developerDir).doesNotContain("__BAZEL_XCODE_")
    assertThat(info.sdkRoot).startsWith("/")
    assertThat(info.sdkRoot).doesNotContain("__BAZEL_XCODE_")

    // the versions are only known if Xcode is installed, the command line tools are not enough
    assertThat(info.xcodeVersion.isEmpty()).isEqualTo(info.sdkVersion.isEmpty())
  }

  @Test
  fun testHasPlaceholdersInOptions() {
    val info = target.cToolchainIdeInfo
    assertThat(info.cOptionList.any { it.contains("__BAZEL_XCODE_SDKROOT__") }).isTrue()
    assertThat(info.cppOptionList.any { it.contains("__BAZEL_XCODE_SDKROOT__") }).isTrue()
  }
}
