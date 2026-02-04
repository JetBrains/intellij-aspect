package com.intellij.aspect.testing.tests.general

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TransitionTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testFindTargets() {
    val targets = aspect.findTargets("//:main")
    assertThat(targets).hasSize(3)
    assertThat(targets.map { it.key.configuration }.toSet()).hasSize(3)
  }
}