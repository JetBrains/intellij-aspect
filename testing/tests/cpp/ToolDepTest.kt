package com.intellij.aspect.testing.tests.cpp

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ToolDepTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testSkipToolUnderExecConfig() {
    val targets = aspect.findTargets("//:tool")
    assertThat(targets).hasSize(1)
  }
}
