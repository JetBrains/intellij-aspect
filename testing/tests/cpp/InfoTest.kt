package com.intellij.aspect.testing.tests.cpp

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class InfoTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testSourcesList() {
    val info = aspect.findCIdeInfo("//lib:lib")
    assertThat(info.ruleContext.sourcesList.map { it.relativePath }).contains("lib/source.cc")
  }

  @Test
  fun testHeadersList() {
    val info = aspect.findCIdeInfo("//lib:lib")
    assertThat(info.ruleContext.headersList.map { it.relativePath }).contains("lib/header.h")
  }

  @Test
  fun testCoptsList() {
    val info = aspect.findCIdeInfo("//lib:lib")
    assertThat(info.ruleContext.coptsList).contains("-DLIB_COPT")
  }

  @Test
  fun testIncludePrefix() {
    val info = aspect.findCIdeInfo("//lib:lib")
    assertThat(info.ruleContext.includePrefix).isEqualTo("prefixed")
  }

  @Test
  fun testPublicDefinesOnTarget() {
    val info = aspect.findCIdeInfo("//lib:lib")
    assertThat(info.compilationContext.definesList).contains("PUBLIC_DEFINE")
  }

  @Test
  fun testLocalDefinesOnTarget() {
    val info = aspect.findCIdeInfo("//lib:lib")
    assertThat(info.compilationContext.definesList).contains("LOCAL_DEFINE")
  }

  @Test
  fun testIncludesOnTarget() {
    val info = aspect.findCIdeInfo("//lib:lib")
    assertThat(info.compilationContext.includesList).isNotEmpty()
  }

  @Test
  fun testPublicDefinesPropagated() {
    val info = aspect.findCIdeInfo("//:main")
    assertThat(info.compilationContext.definesList).contains("PUBLIC_DEFINE")
  }

  @Test
  fun testLocalDefinesNotPropagated() {
    val info = aspect.findCIdeInfo("//:main")
    assertThat(info.compilationContext.definesList).doesNotContain("LOCAL_DEFINE")
  }
}
