package com.intellij.aspect.testing.tests.cpp

import com.google.common.truth.Truth.assertThat
import com.google.devtools.intellij.ideinfo.IdeInfo.*
import com.google.protobuf.Message
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.lib.assertNotNull
import com.intellij.aspect.testing.rules.lib.isMacOS
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

  private inline fun <reified T : Message> findToolchainInfo(label: String, fieldNumber: Int): T {
    val descriptor = assertNotNull(TargetIdeInfo.getDescriptor().findFieldByNumber(fieldNumber))

    val target = aspect.findTarget(label).depsList.asSequence()
      .map { aspect.findTarget(it.target.label) }
      .filter { it.hasField(descriptor) }
      .map { it.getField(descriptor) }
      .filterIsInstance<T>()
      .firstOrNull()

    return assertNotNull(target)
  }

  @Test
  fun teatHasToolchain() {
    val info = findToolchainInfo<CToolchainIdeInfo>("//:main", TargetIdeInfo.C_TOOLCHAIN_IDE_INFO_FIELD_NUMBER)
    assertThat(info.cCompiler).isNotEmpty()
    assertThat(info.cCompiler).isEqualTo(info.cppCompiler)
  }

  @Test
  fun testHasXcodeInfo() {
    assumeTrue(aspect.bazelVersion(min = 8))
    assumeTrue(isMacOS())

    val info = findToolchainInfo<XcodeIdeInfo>("//:main", TargetIdeInfo.XCODE_IDE_INFO_FIELD_NUMBER)
    assertThat(info.xcodeVersion).isNotEmpty()
    assertThat(info.macosSdkVersion).isNotEmpty()
  }
}
