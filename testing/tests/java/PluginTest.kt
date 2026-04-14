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

package com.intellij.aspect.testing.tests.java

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PluginTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testLib() {
    val target = aspect.findTarget("//:java_lib")
    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.kind).isEqualTo("java_library")
    assertThat(target.executable).isFalse()
    assertThat(target.srcsList.map { it.relativePath }).containsExactly("JavaLib.java")
    assertThat(target.depsList.map { it.target.label }).contains("//helper:lib")

    // JavaCommon
    val binaryJars = target.javaCommon.jarsList.flatMap { it.binaryJarsList }
    assertThat(binaryJars.size).isEqualTo(1)
    assertThat(binaryJars[0].relativePath).isEqualTo("libjava_lib.jar")

    val generatedBinaryJars = target.javaCommon.generatedJarsList.flatMap { it.binaryJarsList }
    assertThat(generatedBinaryJars.size).isEqualTo(1)
    assertThat(generatedBinaryJars[0].relativePath).isNotEqualTo("libjava_lib.jar")
    assertThat(generatedBinaryJars[0].relativePath).startsWith("libjava_lib")
  }

  @Test
  fun testHelper() {
    val target = aspect.findTarget("//helper:lib")
    assertThat(target.kind).isEqualTo("java_library")
    assertThat(target.executable).isFalse()
    assertThat(
      target.srcsList.map {
        it.relativePath
      },
    ).containsExactly(
      "helper/src/com/example/processor/GenerateHelper.java",
      "helper/src/com/example/processor/HelperProcessor.java",
    )

    // JavaCommon
    val binaryJars = target.javaCommon.jarsList.flatMap { it.binaryJarsList }
    assertThat(binaryJars.size).isEqualTo(1)
    assertThat(binaryJars[0].relativePath).isEqualTo("helper/liblib.jar")
    assertThat(target.javaCommon.generatedJarsList).isEmpty()
  }
}
