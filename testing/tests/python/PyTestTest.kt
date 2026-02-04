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
class PyTestTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testPyTest() {
    val target = aspect.findTarget("//:test")
    assertThat(target.hasPyIdeInfo()).isTrue()

    assertThat(target.kindString).isEqualTo("py_test")
    assertThat(target.pyIdeInfo.sourcesList.map { it.relativePath }).containsExactly("test.py")
    assertThat(target.pyIdeInfo.srcsVersion).isEqualTo(PythonSrcsVersion.SRC_PY3)
    assertThat(target.pyIdeInfo.pythonVersion).isEqualTo(PythonVersion.PY3)
  }
}
