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
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.Dependency.DependencyType
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.assertThatDeps
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
    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.kind).isEqualTo("java_binary")
    assertThat(target.hasExecutableInfo()).isTrue()
    assertThat(target.testonly).isFalse()

    // Sources are reported correctly
    assertThat(target.srcsList.size).isEqualTo(1)
    assertThat(target.srcsList[0].isSource).isTrue()
    assertThat(target.srcsList[0].relativePath).isEqualTo("Main.java")

    // Dependencies are reported correctly
    assertThatDeps(target.depsList).withType(DependencyType.COMPILE_TIME).labels().contains("//lib:util")
    assertThatDeps(target.depsList).labels().doesNotContain("//lib:moreutils")

    // JVM-info is reported correctly
    val jvmInfo = target.jvmTargetInfo
    assertThat(jvmInfo.mainClass).isEqualTo("com.intellij.aspect.testing.fixtures.java.simple.Main")
    assertThat(jvmInfo.resourcesCount).isEqualTo(1)
    assertThat(jvmInfo.resourcesList[0].isSource).isTrue()
    assertThat(jvmInfo.resourcesList[0].relativePath.endsWith("data.txt")).isTrue()

    // The toolchain dependency is reported
    val toolchains = target.depsList.map { aspect.findTarget(it.target.label) }.filter { it.hasJavaToolchainInfo() }
    assertThat(toolchains).isNotEmpty()

    val toolchain = toolchains.first()
    assertThat(toolchain.javaToolchainInfo.sourceVersion).isEqualTo("21")
    assertThat(toolchain.javaToolchainInfo.javaHome.rootPath).isNotEmpty()
    assertThat(toolchain.javaToolchainInfo.javaHome.isExternal).isTrue()
    assertThat(toolchain.javaToolchainInfo.javaHomePath).startsWith("external/rules_java")
    assertThat(toolchain.javaToolchainInfo.javaHomePath).contains("remotejdk")
    assertThat(toolchain.javaToolchainInfo.bootClasspathJavaHome.rootPath).contains("remotejdk")
    assertThat(toolchain.javaToolchainInfo.bootClasspathJavaHomePath).startsWith("external/rules_java")
    assertThat(toolchain.javaToolchainInfo.bootClasspathJavaHomePath).contains("remotejdk")
  }

  @Test
  fun testFindsLib() {
    val target = aspect.findTarget("//lib:util")
    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.kind).isEqualTo("java_library")
    assertThat(target.hasExecutableInfo()).isFalse()
    assertThat(target.testonly).isFalse()

    // Sources are reported correctly
    assertThat(target.srcsList.size).isEqualTo(1)
    assertThat(target.srcsList[0].isSource).isTrue()
    assertThat(target.srcsList[0].relativePath).isEqualTo("lib/Util.java")

    // Dependencies are reported correctly
    assertThatDeps(target.depsList).withType(DependencyType.COMPILE_TIME).labels().contains("//lib:moreutils")

    // JavaInfo related information is reported correctly
    assertThat(target.javaProvider.fullCompileJarsCount).isEqualTo(1)
    assertThat(target.javaProvider.fullCompileJarsList[0].relativePath).isEqualTo("lib/libutil.jar")

    // JVM-info is reported correctly
    val jvmInfo = target.jvmTargetInfo

    // Common information is reported correctly
    assertThat(target.javaCommon.javacOptsList).contains("-Xep:ReturnValueIgnored:WARN")
    assertThat(target.javaProvider.hasApiGeneratingPlugins).isFalse()
    val binJars = target.javaCommon.jarsList.flatMap { it.binaryJarsList }
    assertThat(binJars.size).isEqualTo(1)
    assertThat(binJars[0].relativePath).startsWith("lib/")
    assertThat(target.javaCommon.jarsList.flatMap { it.sourceJarsList }.size).isEqualTo(1)
    assertThat(target.javaCommon.jarsList.flatMap { it.interfaceJarsList }.size).isEqualTo(1)
    assertThat(target.javaCommon.jdepsList.size).isEqualTo(1)

    // The toolchain dependency is reported
    val toolchains =
      target.depsList.map { aspect.findTarget(it.target.label) }.filter { it.hasJavaToolchainInfo() }
    assertThat(toolchains).isNotEmpty()
    assertThat(toolchains.first().javaToolchainInfo.sourceVersion).isEqualTo("21")
    assertThat(toolchains.first().javaToolchainInfo.javaHome.rootPath).isNotEmpty()
  }

  @Test
  fun testFindsSubLib() {
    val target = aspect.findTarget("//lib:moreutils")
    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.kind).isEqualTo("java_library")
    assertThat(target.hasExecutableInfo()).isFalse()
  }

  @Test
  fun testFindTest() {
    val target = aspect.findTarget("//test:util")

    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.kind).isEqualTo("java_test")
    assertThat(target.hasExecutableInfo()).isTrue()
    assertThat(target.testonly).isTrue()

    // Dependencies are reported correctly
    assertThatDeps(target.depsList).withType(DependencyType.COMPILE_TIME).labels().contains("//lib:util")
    assertThatDeps(target.depsList).labels().doesNotContain("//lib:moreutils")

    // Test environment is reported correctly
    assertThat(target.envInheritList).isEqualTo(listOf("PROPERTIES"))
    assertThat(target.envMap).isEqualTo(mapOf("PATH" to "/opt/test/bin:/bin:/usr/bin"))
  }

  @Test
  fun testOutputGroups() {
    // source files (including prebuilt jars such as the test runner) are never part of the sync
    // group, so nothing has to be built for a plain sync and the group is absent
    assertThat(aspect.hasOutputGroup(OutputGroups.SYNC)).isFalse()

    val buildFiles = aspect.findOutputGroup(OutputGroups.BUILD)
    assertThat(buildFiles.filter { it.endsWith("main.jar") }).isNotEmpty()
    assertThat(buildFiles.filter { it.contains("materialized") && it.endsWith("main.jdeps") }).isNotEmpty()
    assertThat(buildFiles.filter { it.endsWith("lib/libutil.jar") }).isNotEmpty()
    assertThat(buildFiles.filter { it.contains("materialized") && it.endsWith("util.jdeps") }).isNotEmpty()
  }
}
