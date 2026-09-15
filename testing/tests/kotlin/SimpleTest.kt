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
import com.google.devtools.intellij.aspect.Common.ArtifactLocation
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.KotlinTargetInfo
import com.intellij.aspect.lib.OutputGroups
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
    assertThat(target.hasExecutableInfo()).isTrue()

    // Sources are reported correctly
    assertThat(target.srcsList.size).isEqualTo(1)
    assertThat(target.srcsList[0].isSource).isFalse()
    assertThat(target.srcsList[0].relativePath).isEqualTo("Main.kt")

    // JVM Info
    assertThat(target.jvmTargetInfo.mainClass).isEqualTo("org.example.MainKt")

    // Common information
    if (!aspect.isBCRDeployment()) {
      assertThat(target.javaCommon.jarsList.flatMap { it.binaryJarsList }.size).isEqualTo(1)
      assertThat(target.javaCommon.jarsList.flatMap { it.sourceJarsList }.size).isEqualTo(1)
      assertThat(target.javaCommon.jarsList.flatMap { it.interfaceJarsList }.size).isEqualTo(1)
    }

    // Kotlin-specific information is present
    assertStdlibJars(target.kotlinTargetInfo)
    assertThat(target.kotlinTargetInfo.languageVersion).isNotEmpty()
  }

  /**
   * The stdlibs come as jar outputs read from the toolchain's JavaInfo: the default `jvm_stdlibs` of
   * rules_kotlin, each as its distribution jar. The flat `stdlibs` list is the class jars of those
   * entries, and every reported jar is in the build output group, so the IDE can open it.
   */
  @Suppress("DEPRECATION") // stdlibs is kept for older readers; this checks it stays consistent.
  private fun assertStdlibJars(info: KotlinTargetInfo) {
    val buildFiles = aspect.findOutputGroup(OutputGroups.BUILD)

    fun assertBuilt(jar: ArtifactLocation) {
      assertThat(jar.isSource).isTrue()
      assertThat(jar.isExternal).isTrue()
      assertThat(buildFiles.filter { it.endsWith("/" + jar.relativePath) }).isNotEmpty()
    }

    val binaryJars = info.stdlibJarsList.map { output ->
      assertThat(output.binaryJarsList).hasSize(1)
      output.binaryJarsList.single()
    }
    assertThat(binaryJars.map { it.relativePath }).containsExactly(
      "lib/annotations-13.0.jar",
      "lib/kotlin-stdlib.jar",
      "lib/kotlin-stdlib-jdk7.jar",
      "lib/kotlin-stdlib-jdk8.jar",
    )
    binaryJars.forEach(::assertBuilt)

    // The fixtures build with rules_kotlin 2.2.0 and 2.3.20. Their toolchain wraps the stdlib files
    // into a new JavaInfo without the source jars the imports declare, so none is reported. When the
    // fixture matrix moves to a release that keeps them, the stdlib outputs carry the distribution's
    // `-sources.jar` twins and this assertion changes to expect them.
    info.stdlibJarsList.forEach { output ->
      assertThat(output.interfaceJarsList).isEmpty()
      assertThat(output.sourceJarsList).isEmpty()
    }

    assertThat(binaryJars).containsExactlyElementsIn(info.stdlibsList).inOrder()
  }

  @Test
  fun testFindsLib() {
    val target = aspect.findTarget("//lib:util")
    assertThat(target.hasKotlinTargetInfo()).isTrue()
    assertThat(target.kind).isEqualTo("kt_jvm_library")
    assertThat(target.hasExecutableInfo()).isFalse()

    // Sources are reported correctly
    assertThat(target.srcsList.size).isEqualTo(1)
    assertThat(target.srcsList[0].isSource).isTrue()
    assertThat(target.srcsList[0].relativePath).isEqualTo("lib/Util.kt")

    // Common information
    if (aspect.isBCRDeployment()) {
      // In BCR deployment, we always run all modules; so additional information can come from the JavaInfo provider
      assertThat(
        target.javaCommon.jarsList.flatMap {
          it.binaryJarsList
        }.map { it.relativePath }.toSet(),
      ).containsExactly("lib/util.jar")
      assertThat(
        target.javaCommon.jarsList.flatMap {
          it.sourceJarsList
        }.map { it.relativePath }.toSet(),
      ).containsExactly("lib/util-sources.jar")
      assertThat(
        target.javaCommon.jarsList.flatMap {
          it.interfaceJarsList
        }.map { it.relativePath }.toSet(),
      ).containsExactly("lib/util.abi.jar")
    } else {
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
    }

    // Kotlin-specific information is present
    assertStdlibJars(target.kotlinTargetInfo)
    assertThat(target.kotlinTargetInfo.languageVersion).isNotEmpty()
  }

  @Test
  fun testOutputs() {
    val syncFiles = aspect.findOutputGroup(OutputGroups.SYNC)
    assertThat(syncFiles.filter { it.endsWith("/kotlin-stdlib.jar") }).isNotEmpty()
    assertThat(syncFiles.filter { it.endsWith("/main.jar") }).isEmpty()

    val buildFiles = aspect.findOutputGroup(OutputGroups.BUILD)
    assertThat(buildFiles.filter { it.endsWith("/main.jar") }).isNotEmpty()
    assertThat(buildFiles.filter { it.endsWith("/lib/util.jar") }).isNotEmpty()
    assertThat(buildFiles.filter { it.endsWith("Main.kt") }).isNotEmpty()
  }
}
