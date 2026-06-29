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
class KspTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testKspPluginReported() {
    val target = aspect.findTarget("//:A")
    assertThat(target.hasKotlinTargetInfo()).isTrue()
    assertThat(target.kind).isEqualTo("kt_jvm_library")
    assertThat(target.kotlinTargetInfo.exportedCompilerPluginTargetsFromDepsList)
      .contains("@@//processor:hello-processor")
  }

  @Test
  fun testProcessorLibrary() {
    val processor = aspect.findTarget("//processor:processor_lib")
    assertThat(processor.hasKotlinTargetInfo()).isTrue()
    assertThat(processor.kind).isEqualTo("kt_jvm_library")

    val annotation = aspect.findTarget("//processor:annotation")
    assertThat(annotation.hasKotlinTargetInfo()).isTrue()
    assertThat(annotation.kind).isEqualTo("kt_jvm_library")
  }

  @Test
  fun testGeneratedJars() {
    val target = aspect.findTarget("//:A")
    val generatedBinaryJars = target.javaCommon.generatedJarsList.flatMap { it.binaryJarsList }
    val generatedSourceJars = target.javaCommon.generatedJarsList.flatMap { it.sourceJarsList }
    assertThat(generatedBinaryJars).isNotEmpty()
    assertThat(generatedSourceJars).isNotEmpty()
  }
}
