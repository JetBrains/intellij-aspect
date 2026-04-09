/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.aspect.testing.tests.java

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MacroTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testFindFoo() {
    val target = aspect.findTarget("//:Foo")
    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.kind).isEqualTo("java_library")
    assertThat(target.executable).isFalse()
    assertThat(target.generatorName).isEqualTo("LotsOfLibraries")
    assertThat(target.srcsCount).isEqualTo(1)
    assertThat(target.srcsList[0].relativePath).isEqualTo("Foo.java")
  }

  @Test
  fun testFindBar() {
    val target = aspect.findTarget("//:Bar")
    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.kind).isEqualTo("java_library")
    assertThat(target.executable).isFalse()
    assertThat(target.generatorName).isEqualTo("LotsOfLibraries")
    assertThat(target.srcsCount).isEqualTo(1)
    assertThat(target.srcsList[0].relativePath).isEqualTo("Bar.java")
  }
}
