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
import com.google.devtools.intellij.ideinfo.IdeInfo
import com.google.devtools.intellij.ideinfo.IdeInfo.PyIdeInfo.PythonSrcsVersion
import com.google.devtools.intellij.ideinfo.IdeInfo.PyIdeInfo.PythonVersion
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.tests.lib.dependencyLabels
import com.intellij.aspect.testing.tests.lib.relativeArtifactPath
import org.checkerframework.checker.units.qual.t
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WithImportsTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testMain() {
    val target = aspect.findTarget("//:main")
    assertThat(target.kind).isEqualTo("py_binary")
    assertThat(target.depsList).dependencyLabels(IdeInfo.Dependency.DependencyType.COMPILE_TIME)
      .containsExactly("//:lib")

    assertThat(target.pythonTargetInfo.version).isEqualTo("PY3")
    assertThat(target.pythonTargetInfo.interpreter.rootPath).containsMatch("rules_python")
    assertThat(target.pythonTargetInfo.interpreter.relativePath).endsWith("python3")
  }

  @Test
  fun testLib() {
    val target = aspect.findTarget("//:lib")
    assertThat(target.kind).isEqualTo("py_library")
    assertThat(target.depsList).dependencyLabels(IdeInfo.Dependency.DependencyType.COMPILE_TIME)
      .containsExactly("//:pure_lib")
    assertThat(target.pythonTargetInfo.version).isEqualTo("PY3")
    assertThat(target.pythonTargetInfo.importsList).containsExactly("src")
    assertThat(target.pythonTargetInfo.generatedSourcesList).relativeArtifactPath()
      .containsExactly("src/lib/foo/__init__.py", "src/lib/foo/foo.py")
  }

  @Test
  fun testPureLib() {
    val target = aspect.findTarget("//:pure_lib")
    assertThat(target.kind).isEqualTo("py_library")
    assertThat(target.depsList).isEmpty()
    assertThat(target.srcsList).relativeArtifactPath().containsExactly("src/lib/foo/__init__.py", "src/lib/foo/foo.py")
  }
}
