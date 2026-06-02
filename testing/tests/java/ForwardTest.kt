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
import com.google.devtools.intellij.ideinfo.IdeInfo.Dependency.DependencyType
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.tests.lib.dependencyLabels
import com.intellij.aspect.testing.tests.lib.relativeArtifactPath
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ForwardTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testLibs() {
    val target = aspect.findTarget("//lib:util")
    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.kind).isEqualTo("java_library")
    assertThat(target.javaCommon.jdepsCount).isEqualTo(1)
    assertThat(target.javaCommon.jdepsList[0].relativePath).startsWith("lib")
    assertThat(target.javaCommon.jdepsList[0].relativePath).contains("materialized")
    assertThat(target.javaCommon.jdepsList[0].relativePath).endsWith("util.jdeps")

    val nestedTarget = aspect.findTarget("//lib:nested/util")
    assertThat(nestedTarget.hasJavaProvider()).isTrue()
    assertThat(nestedTarget.kind).isEqualTo("java_library")
    assertThat(nestedTarget.javaCommon.jdepsCount).isEqualTo(1)
    assertThat(nestedTarget.javaCommon.jdepsList[0].relativePath).startsWith("lib")
    assertThat(nestedTarget.javaCommon.jdepsList[0].relativePath).contains("materialized")
    assertThat(nestedTarget.javaCommon.jdepsList[0].relativePath).endsWith("util.jdeps")

    assertThat(nestedTarget.javaCommon.jdepsList[0].relativePath).isNotEqualTo(
      target.javaCommon.jdepsList[0].relativePath,
    )
  }

  @Test
  fun testForward() {
    val target = aspect.findTarget("//forwarded:fwd")
    assertThat(target.hasJavaProvider()).isTrue()
    assertThat(target.kind).isEqualTo("forward")
    assertThat(target.javaCommon.jdepsCount).isEqualTo(1)
    assertThat(target.javaCommon.jdepsList[0].relativePath).startsWith("forwarded")
    assertThat(target.javaCommon.jdepsList[0].relativePath).contains("materialized")
    assertThat(target.javaCommon.jdepsList[0].relativePath).endsWith("util.jdeps")

    val targetTwo = aspect.findTarget("//forwarded:fwd2")
    assertThat(targetTwo.hasJavaProvider()).isTrue()
    assertThat(targetTwo.kind).isEqualTo("forward")
    assertThat(targetTwo.javaCommon.jdepsCount).isEqualTo(1)
    assertThat(targetTwo.javaCommon.jdepsList[0].relativePath).isEqualTo(target.javaCommon.jdepsList[0].relativePath)

    val targetTag = aspect.findTarget("//forwarded:fwdtag")
    assertThat(targetTag.hasJavaProvider()).isTrue()
    assertThat(targetTag.kind).isEqualTo("forward")
    assertThat(targetTag.javaCommon.jdepsCount).isEqualTo(1)
    assertThat(targetTag.javaCommon.jdepsList[0].relativePath).startsWith("forwarded")
    assertThat(targetTag.javaCommon.jdepsList[0].relativePath).contains("materialized")
    assertThat(targetTag.javaCommon.jdepsList[0].relativePath).endsWith("util.jdeps")

    val nestedTarget = aspect.findTarget("//forwarded:nested/fwd")
    assertThat(nestedTarget.hasJavaProvider()).isTrue()
    assertThat(nestedTarget.kind).isEqualTo("forward")
    assertThat(nestedTarget.javaCommon.jdepsCount).isEqualTo(1)
    assertThat(nestedTarget.javaCommon.jdepsList[0].relativePath).startsWith("forwarded")
    assertThat(nestedTarget.javaCommon.jdepsList[0].relativePath).contains("materialized")
    assertThat(nestedTarget.javaCommon.jdepsList[0].relativePath).endsWith("util.jdeps")
    assertThat(nestedTarget.javaCommon.jdepsList[0].relativePath).isNotEqualTo(
      target.javaCommon.jdepsList[0].relativePath,
    )
  }
}
