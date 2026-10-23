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
package com.intellij.aspect.testing.tests.deploy

import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.google.devtools.build.runfiles.Runfiles
import com.intellij.aspect.lib.AspectDefinition
import com.intellij.aspect.lib.INTELLIJ_ASPECT
import com.intellij.aspect.lib.Rules
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.nio.file.Path
import java.util.zip.ZipFile

@RunWith(JUnit4::class)
class AspectDefinitionTest {

  @Test
  fun testModuleFilesExist() {
    val archive = requireNotNull(System.getenv("ARCHIVE_IDE"))
    val archivePath = Runfiles.preload().unmapped().rlocation(archive)

    val entries = ZipFile(archivePath).use { zip ->
      zip.entries().asSequence().map { it.name }.toSet()
    }

    val modules = INTELLIJ_ASPECT.modules
    assertThat(modules).isNotEmpty()

    modules.forEach { module ->
      assertWithMessage("module declares missing file: %s", module.file)
        .that(entries)
        .contains("modules/${module.file}.bzl")
    }
  }

  @Test
  fun testBundledLocation() {
    val location = INTELLIJ_ASPECT.location

    assertThat(location).isEqualTo(AspectDefinition.Location(Path.of("config", "aspect.bzl"), "intellij_aspect"))
    assertThat(location.toString()).isEqualTo("config:aspect.bzl%intellij_aspect")

    @Suppress("DEPRECATION")
    assertThat(com.intellij.aspect.lib.ASPECT_NAME).isEqualTo("config:aspect.bzl%intellij_aspect")
  }

  @Test
  fun testModulesForRules() {
    val files = INTELLIJ_ASPECT.modulesFor(setOf(Rules.JAVA)).map { it.file }

    assertThat(files).contains("java_info")
    assertThat(files).contains("run_info") // no ruleset requirements, always enabled
    assertThat(files).doesNotContain("cc_info")
    assertThat(files).doesNotContain("rules_proto_info")
  }

  @Test
  fun testLegacyRulesProto() {
    val files = INTELLIJ_ASPECT.modulesFor(setOf(Rules.LEGACY_RULES_PROTO)).map { it.file }

    assertThat(files).contains("rules_proto_info")
    assertThat(files).contains("proto_info")
    assertThat(files).doesNotContain("protobuf_info")
  }

  @Test
  fun testGenerateAspectFile() {
    val content = INTELLIJ_ASPECT.generateAspectFile(Path.of("aspect", "x"), setOf(Rules.JAVA))

    assertThat(content).contains("""load("//aspect/x/intellij:aspect.bzl", "intellij_configure_aspect")""")
    assertThat(content).contains("""load("//aspect/x/modules:java_info.bzl", java_info = "module")""")
    assertThat(content).doesNotContain("cc_info")
    assertThat(content).contains("intellij_aspect = intellij_configure_aspect(")
    assertThat(content).contains("""container = "//aspect/x/config:module_container"""")
  }
}
