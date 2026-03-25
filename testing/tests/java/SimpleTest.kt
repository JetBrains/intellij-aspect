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
class SimpleTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testFindsMain() {
    val target = aspect.findTarget("//:main")
    assertThat(target.hasJavaIdeInfo()).isTrue()
    assertThat(target.kind).isEqualTo("java_binary")
    assertThat(target.executable).isTrue()

    // Sources are reported correctly
    assertThat(target.srcsList.size).isEqualTo(1)
    assertThat(target.srcsList[0].isSource).isTrue()
    assertThat(target.srcsList[0].relativePath).isEqualTo("Main.java")

    // Dependencies are reported correctly
    assertThat(target.depsList.map { it.target.label }).contains("//lib:util")

    // JVM-info is reported correctly
    val jvmInfo = target.jvmIdeInfo
    assertThat(jvmInfo.mainClass).isEqualTo("com.intellij.aspect.testing.fixtures.java.simple.Main")

    // The toolchain dependency is reported
    val toolchains =
      target.depsList.map { aspect.findTarget(it.target.label) }.filter { it.hasJavaToolchainIdeInfo() }
    assertThat(toolchains).isNotEmpty()
    assertThat(toolchains.first().javaToolchainIdeInfo.sourceVersion).isEqualTo("21")
    assertThat(toolchains.first().javaToolchainIdeInfo.javaHome).isNotEmpty()
    assertThat(toolchains.first().javaToolchainIdeInfo.bootClasspathJavaHome).contains("remotejdk")
  }

  @Test
  fun testFindsLib() {
    val target = aspect.findTarget("//lib:util")
    assertThat(target.hasJavaIdeInfo()).isTrue()
    assertThat(target.kind).isEqualTo("java_library")
    assertThat(target.executable).isFalse()

    // Sources are reported correctly
    assertThat(target.srcsList.size).isEqualTo(1)
    assertThat(target.srcsList[0].isSource).isTrue()
    assertThat(target.srcsList[0].relativePath).isEqualTo("lib/Util.java")

    // JavaInfo related information is reported correctly
    assertThat(target.javaIdeInfo.fullCompileJarsCount).isEqualTo(1)
    assertThat(target.javaIdeInfo.fullCompileJarsList[0].relativePath).isEqualTo("lib/libutil.jar")

    // JVM-info is reported correctly
    val jvmInfo = target.jvmIdeInfo
    assertThat(jvmInfo.javacOptsList).isEqualTo(listOf("-Xep:ReturnValueIgnored:WARN"))

    // Common information is reported correctly
    assertThat(target.javaIdeInfo.hasApiGeneratingPlugins).isFalse()
    val binJars = target.javaCommon.jarsList.flatMap { it.binaryJarsList }
    assertThat(binJars.size).isEqualTo(1)
    assertThat(binJars[0].relativePath).startsWith("lib/")
    assertThat(target.javaCommon.jarsList.flatMap { it.sourceJarsList }.size).isEqualTo(1)
    assertThat(target.javaCommon.jarsList.flatMap { it.interfaceJarsList }.size).isEqualTo(1)
    assertThat(target.javaCommon.jarsList.flatMap { it.jdepsList }.size).isEqualTo(1)

    // The toolchain dependency is reported
    val toolchains =
      target.depsList.map { aspect.findTarget(it.target.label) }.filter { it.hasJavaToolchainIdeInfo() }
    assertThat(toolchains).isNotEmpty()
    assertThat(toolchains.first().javaToolchainIdeInfo.sourceVersion).isEqualTo("21")
    assertThat(toolchains.first().javaToolchainIdeInfo.javaHome).isNotEmpty()
  }

  @Test
  fun testFindTest() {
    val target = aspect.findTarget("//test:util")

    assertThat(target.hasJavaIdeInfo()).isTrue()
    assertThat(target.kind).isEqualTo("java_test")
    assertThat(target.executable).isTrue()

    // Dependencies are reported correctly
    assertThat(target.depsList.map { it.target.label }).contains("//lib:util")

    // Test environment is reported correctly
    assertThat(target.envInheritList).isEqualTo(listOf("PROPERTIES"))
    assertThat(target.envMap).isEqualTo(mapOf("PATH" to "/opt/test/bin:/bin:/usr/bin"))
  }
}
