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

package com.intellij.aspect.testing.tests.proto

import com.google.common.truth.Truth.assertThat
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.Dependency.DependencyType
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.private.lib.utils.isWindows
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
  fun testConsumer() {
    val target = aspect.findTarget("//consumerJava:main")
    assertThat(target.kind).isEqualTo("java_binary")
    assertThat(target.depsList).dependencyLabels(DependencyType.COMPILE_TIME).contains("//libB:lib_b_java")
  }

  @Test
  fun testLibB() {
    val javaLib = aspect.findTarget("//libB:lib_b_java")
    assertThat(javaLib.kind).isEqualTo("java_proto_library")
    assertThat(javaLib.depsList.map { it.target.label }).contains("//libB:lib_b")

    val protoLib = aspect.findTarget("//libB:lib_b")
    assertThat(protoLib.kind).isEqualTo("proto_library")
    assertThat(protoLib.depsList.map { it.target.label }).contains("//libA:lib_a")
    assertThat(
      protoLib.protobufTargetInfo.sourceMappingsList.filter {
        it.protoFile.relativePath.endsWith("lib_b.proto")
      }.map { it.importPath },
    ).isEqualTo(listOf("my_prefix/libB/lib_b.proto"))
  }

  @Test
  fun testLibA() {
    val protoLib = aspect.findTarget("//libA:lib_a")
    assertThat(protoLib.kind).isEqualTo("proto_library")
    assertThat(
      protoLib.protobufTargetInfo.sourceMappingsList.filter {
        it.protoFile.relativePath.endsWith("lib_a.proto")
      }.map { it.importPath },
    ).isEqualTo(listOf("my_prefix/libA/lib_a.proto"))
  }

  @Test
  fun testOutputGroups() {
    if (!isWindows()) {
      val buildFiles = aspect.findOutputGroup(OutputGroups.BUILD)
      assertThat(buildFiles.filter { it.contains("consumerJava/main.jar") }).isNotEmpty()
      assertThat(buildFiles.filter { it.contains("consumerJava/main-src.jar") }).isNotEmpty()
      assertThat(buildFiles.filter { it.contains("libB/liblib_b") }).isNotEmpty()
      assertThat(buildFiles.filter { it.contains("libA/liblib_a") }).isNotEmpty()
    }
  }

  @Test
  fun testAspectIds() {
    val aspectIds = aspect.findTarget("//libA:lib_a").key.aspectIdsList

    assertThat(aspectIds).hasSize(1)
    assertThat(aspectIds[0]).endsWith("java_proto_aspect")
  }
}
