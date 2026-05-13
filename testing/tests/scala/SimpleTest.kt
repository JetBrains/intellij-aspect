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

package com.intellij.aspect.testing.tests.scala

import com.google.common.truth.Truth.assertThat
import com.google.devtools.intellij.ideinfo.IdeInfo.Dependency.DependencyType
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.tests.lib.dependencyLabels
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SimpleTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testFindsMain() {
    val target = aspect.findTarget("//src/main/com/example/foo:example-lib")
    assertThat(target.kind).isEqualTo("scala_library")
    assertThat(target.scalaTargetInfo.compilerClasspathList).isNotEmpty()
    assertThat(target.scalaTargetInfo.scalatestClasspathTargetsList).isEmpty()
  }

  @Test
  fun testFindsTest() {
    val target = aspect.findTarget("//src/test/com/example/foo:test")
    assertThat(target.kind).isEqualTo("scala_test")
    assertThat(target.depsList.map { it.target.label }).contains("//src/main/com/example/foo:example-lib")
    assertThat(target.scalaTargetInfo.compilerClasspathList).isNotEmpty()
    assertThat(target.scalaTargetInfo.scalatestClasspathTargetsList).isNotEmpty()
  }
}
