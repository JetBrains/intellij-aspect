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

package com.intellij.aspect.testing.tests.kotlin

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.testing.rules.fixture.AspectFixture
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
    val target = aspect.findTarget("//:main")
    assertThat(target.hasKotlinTargetInfo()).isTrue()
    assertThat(target.kind).isEqualTo("kt_jvm_binary")
    assertThat(target.executable).isTrue()

    // Sources are reported correctly
    assertThat(target.srcsList.size).isEqualTo(1)
    assertThat(target.srcsList[0].isSource).isTrue()
    assertThat(target.srcsList[0].relativePath).isEqualTo("Main.kt")

    // JVM Info
    assertThat(target.jvmTargetInfo.mainClass).isEqualTo("org.example.MainKt")

    // Common information
    assertThat(target.javaCommon.jarsList.flatMap { it.binaryJarsList }.size).isEqualTo(1)
    assertThat(target.javaCommon.jarsList.flatMap { it.sourceJarsList }.size).isEqualTo(1)
    assertThat(target.javaCommon.jarsList.flatMap { it.interfaceJarsList }.size).isEqualTo(1)

    // Kotlin-specific information is present
    assertThat(target.kotlinTargetInfo.stdlibsList).isNotEmpty()
    assertThat(target.kotlinTargetInfo.languageVersion).isNotEmpty()
  }

  @Test
  fun testFindsLib() {
    val target = aspect.findTarget("//lib:util")
    assertThat(target.hasKotlinTargetInfo()).isTrue()
    assertThat(target.kind).isEqualTo("kt_jvm_library")
    assertThat(target.executable).isFalse()

    // Sources are reported correctly
    assertThat(target.srcsList.size).isEqualTo(1)
    assertThat(target.srcsList[0].isSource).isTrue()
    assertThat(target.srcsList[0].relativePath).isEqualTo("lib/Util.kt")

    // Common information
    assertThat(target.javaCommon.jarsList.flatMap { it.binaryJarsList }.size).isEqualTo(1)
    assertThat(target.javaCommon.jarsList.flatMap { it.binaryJarsList }[0].relativePath).isEqualTo("lib/util.jar")
    assertThat(target.javaCommon.jarsList.flatMap { it.sourceJarsList }.size).isEqualTo(1)
    assertThat(
      target.javaCommon.jarsList.flatMap {
        it.sourceJarsList
      }[0].relativePath,
    ).isEqualTo("lib/util-sources.jar")
    assertThat(target.javaCommon.jarsList.flatMap { it.interfaceJarsList }.size).isEqualTo(1)
    assertThat(
      target.javaCommon.jarsList.flatMap {
        it.interfaceJarsList
      }[0].relativePath,
    ).isEqualTo("lib/util.abi.jar")

    // Kotlin-specific information is present
    assertThat(target.kotlinTargetInfo.stdlibsList).isNotEmpty()
    assertThat(target.kotlinTargetInfo.languageVersion).isNotEmpty()
  }
}
