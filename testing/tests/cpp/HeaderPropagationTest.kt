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
package com.intellij.aspect.testing.tests.cpp

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.lib.OutputGroups
import com.intellij.aspect.testing.rules.fixture.AspectFixture
import com.intellij.aspect.testing.rules.utils.assertThatOutputGroup
import com.intellij.aspect.testing.rules.utils.findCIdeInfo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HeaderPropagationTest {

  @Rule
  @JvmField
  val aspect = AspectFixture()

  @Test
  fun testImplementationHeadersStayOnTheirConsumer() {
    val library = aspect.findCIdeInfo("//:library").compilationContext.headersList
    assertThat(library.map { it.relativePath })
      .containsExactly("own.h", "public.h", "textual.inc", "generated.h")
    assertThat(library.single { it.relativePath == "generated.h" }.isSource).isFalse()

    val consumer = aspect.findCIdeInfo("//:consumer").compilationContext.headersList
    assertThat(consumer.map { it.relativePath }).containsExactly("own.h", "public.h", "textual.inc")
    assertThat(consumer).containsExactlyElementsIn(library.filter { it.isSource })
  }

  @Test
  fun testForwardedContextKeepsHeadersOutsideDeps() {
    val expected = aspect.findCIdeInfo("//:consumer").compilationContext.headersList
    val forwarded = aspect.findCIdeInfo("//:forward").compilationContext.headersList
    val consumer = aspect.findCIdeInfo("//:forward_consumer").compilationContext.headersList

    assertThat(forwarded).containsExactlyElementsIn(expected).inOrder()
    assertThat(consumer).containsExactlyElementsIn(expected).inOrder()
    assertThat(forwarded.map { it.relativePath }).doesNotContain("ignored.h")
  }

  @Test
  fun testSourceAndGeneratedOutputGroups() {
    val sync = aspect.findOutputGroup(OutputGroups.SYNC)
    assertThatOutputGroup(sync).containsFile("public.h")
    assertThatOutputGroup(sync).containsFile("own.h")
    assertThatOutputGroup(sync).containsFile("textual.inc")
    assertThatOutputGroup(sync).doesNotContainFile("generated.h")

    val build = aspect.findOutputGroup(OutputGroups.BUILD)
    assertThatOutputGroup(build).containsFile("generated.h")
    assertThatOutputGroup(build).doesNotContainFile("public.h")
  }
}
