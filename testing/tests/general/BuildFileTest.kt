package com.intellij.aspect.testing.tests.general

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BuildFileTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testMainBuildFile() {
    val target = aspect.findTarget("//:main")
    assertThat(target.buildFileArtifactLocation.relativePath).isEqualTo("BUILD")
    assertThat(target.buildFileArtifactLocation.rootPath).isEmpty()
    assertThat(target.buildFileArtifactLocation.isSource).isTrue()
  }

  @Test
  fun testLibBuildFile() {
    val target = aspect.findTarget("//lib:lib")
    assertThat(target.buildFileArtifactLocation.relativePath).isEqualTo("lib/BUILD")
    assertThat(target.buildFileArtifactLocation.rootPath).isEmpty()
    assertThat(target.buildFileArtifactLocation.isSource).isTrue()
  }
}
