package com.intellij.aspect.testing.tests.cpp

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestInfoTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testTestInfoOnTestTarget() {
    val target = aspect.findTarget("//:test")
    assertThat(target.hasTestInfo()).isTrue()
    assertThat(target.testInfo.size).isEqualTo("small")
  }

  @Test
  fun testNoTestInfoOnBinary() {
    val target = aspect.findTarget("//:main")
    assertThat(target.hasTestInfo()).isFalse()
  }

  @Test
  fun testNoTestInfoOnLibrary() {
    val target = aspect.findTarget("//lib:lib")
    assertThat(target.hasTestInfo()).isFalse()
  }
}
