package com.intellij.aspect.testing.tests.cpp

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BuiltinRulesTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testFindsMain() {
    val target = aspect.findTarget("//:main")
    assertThat(target.hasCIdeInfo()).isTrue()
    assertThat(target.kindString).isEqualTo("cc_binary")
  }

  @Test
  fun testFindsLib() {
    val target = aspect.findTarget("//lib:lib")
    assertThat(target.hasCIdeInfo()).isTrue()
    assertThat(target.kindString).isEqualTo("cc_library")
  }

  @Test
  fun testFindsToolchain() {
    val target = aspect.findTarget("//:main").depsList
      .map { aspect.findTarget(it.target.label) }
      .first { it.hasCToolchainIdeInfo() }

    assertThat(target.kindString).isEqualTo("cc_toolchain_alias")
  }
}
