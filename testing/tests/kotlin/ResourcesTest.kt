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
import com.google.devtools.intellij.ideinfo.IdeInfo
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.tests.lib.dependencyLabels
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResourcesTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testALib() {
    val target = aspect.findTarget("//module:a-lib")
    assertThat(target.hasKotlinTargetInfo()).isTrue()
    assertThat(target.kind).isEqualTo("kt_jvm_library")

    assertThat(
      target.depsList.filter {
        it.dependencyType == IdeInfo.Dependency.DependencyType.RUNTIME
      }.map { it.target.label },
    ).containsExactly("//module:foo-res", "//module:bar-res")
  }

  @Test
  fun testFooRes() {
    val target = aspect.findTarget("//module:foo-res")
    assertThat(target.kind).isEqualTo("java_library")
    assertThat(target.jvmTargetInfo.resourceStripPrefix).isEqualTo("module/src/main/foo-res")
    assertThat(target.jvmTargetInfo.resourcesList.size).isEqualTo(1)
    assertThat(target.jvmTargetInfo.resourcesList[0].relativePath)
      .isEqualTo("module/src/main/foo-res/messages/FooBundle.properties")
  }

  @Test
  fun testBarRes() {
    val target = aspect.findTarget("//module:bar-res")
    assertThat(target.kind).isEqualTo("java_import")
  }
}
