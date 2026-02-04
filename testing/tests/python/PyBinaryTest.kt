package com.intellij.aspect.testing.tests.python

import com.google.common.truth.Truth.assertThat
import com.google.devtools.intellij.ideinfo.IdeInfo.PyIdeInfo.PythonSrcsVersion
import com.google.devtools.intellij.ideinfo.IdeInfo.PyIdeInfo.PythonVersion
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PyBinaryTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testPy3Binary() {
    val target = aspect.findTarget("//:simple")
    assertThat(target.hasPyIdeInfo()).isTrue()

    assertThat(target.kindString).isEqualTo("py_binary")
    assertThat(target.pyIdeInfo.sourcesList.map { it.relativePath }).containsExactly("simple.py")
    assertThat(target.pyIdeInfo.srcsVersion).isEqualTo(PythonSrcsVersion.SRC_PY2AND3)
    assertThat(target.pyIdeInfo.pythonVersion).isEqualTo(PythonVersion.PY3)
  }

  @Test
  fun testPyBinaryBuildfileArgs() {
    val info = aspect.findPyIdeInfo("//:simple_with_args")
    assertThat(info.argsList).containsExactly("--ARG1", "--ARG2=fastbuild", "--ARG3='with spaces'")
  }

  @Test
  fun testExpandDataDeps() {
    val info = aspect.findPyIdeInfo("//:simple_with_datadeps")
    assertThat(info.argsList).hasSize(1)
    assertThat(info.argsList.first()).endsWith("datadepfile.txt")
  }
}
