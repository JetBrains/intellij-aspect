package com.intellij.aspect.testing.tests.general

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TargetTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testTargetKeyAspectIds() {
    val target = aspect.findTarget("//:main")
    assertThat(target.key.aspectIdsList).isEmpty()
  }

  @Test
  fun testTargetKeyLabel() {
    val target = aspect.findTarget("//:main")
    assertThat(target.key.label).isEqualTo("//:main")
  }
}