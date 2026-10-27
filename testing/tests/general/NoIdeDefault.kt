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
package com.intellij.aspect.testing.tests.general

import com.google.common.truth.Truth.assertThat
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.Dependency.DependencyType
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.assertThatDeps
import com.intellij.aspect.testing.rules.utils.assertThatOutputGroup
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NoIdeDefault {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testFindsMain() {
    val target = aspect.findTarget("//:main")
    assertThat(target.kind).isEqualTo("java_binary")
    assertThatDeps(target.depsList).withType(DependencyType.COMPILE_TIME).labels().contains("//:dontlook")
  }

  @Test
  fun testNoIdeTarget() {
    val target = aspect.findTarget("//:dontlook")
    assertThat(target.kind).isEqualTo("java_library")
    assertThatDeps(target.depsList).withType(DependencyType.COMPILE_TIME).labels().contains("//:internal")
  }

  @Test
  fun testOutputGroups() {
    val buildFiles = aspect.findOutputGroup(OutputGroups.BUILD)
    assertThatOutputGroup(buildFiles).containsFile("main.jar")
    assertThatOutputGroup(buildFiles).containsFile("libdontlook.jar")
    assertThatOutputGroup(buildFiles).containsFile("libinternal.jar")
  }
}
