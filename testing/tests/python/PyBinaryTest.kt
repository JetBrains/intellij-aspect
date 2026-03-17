/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    assertThat(target.kind).isEqualTo("py_binary")
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
