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
import com.intellij.aspect.lib.INTELLIJ_CC_DEBUG_ASPECT
import com.intellij.aspect.lib.Rules
import com.intellij.aspect.lib.generateAspectFile
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.nio.file.Path
import java.util.zip.ZipFile

// all aspects bundled with the SDK
private val BUNDLED_ASPECTS = listOf(INTELLIJ_ASPECT, INTELLIJ_CC_DEBUG_ASPECT)

@RunWith(JUnit4::class)
class AspectDefinitionTest {

  @Test
  fun testModuleFilesExist() {
    val archive = requireNotNull(System.getenv("ARCHIVE_IDE"))
    val archivePath = Runfiles.preload().unmapped().rlocation(archive)

    val entries = ZipFile(archivePath).use { zip ->
      zip.entries().asSequence().map { it.name }.toSet()
    }

    BUNDLED_ASPECTS.forEach { aspect ->
      assertThat(aspect.modules).isNotEmpty()

      aspect.modules.forEach { module ->
        assertWithMessage("module of %s declares missing file: %s", aspect.location, module.file)
          .that(entries)
          .contains("modules/${module.file}.bzl")
      }
    }
  }

  @Test
  fun testBundledAspectNames() {
    // must match is_intellij_aspect_id in common/common.bzl
    BUNDLED_ASPECTS.forEach { aspect ->
      assertWithMessage("aspect name of %s", aspect.location)
        .that(aspect.location.name.removePrefix("_"))
        .startsWith("intellij_")
    }
  }

  @Test
  fun testBundledDebugLocation() {
    val location = INTELLIJ_CC_DEBUG_ASPECT.location

    assertThat(location).isEqualTo(
      AspectDefinition.Location(Path.of("config", "cc_debug_aspect.bzl"), "intellij_cc_debug_aspect"),
    )
    assertThat(location.toString()).isEqualTo("config:cc_debug_aspect.bzl%intellij_cc_debug_aspect")
  }

  @Test
  fun testAspectModules() {
    val sync = INTELLIJ_ASPECT.modules.map { it.file }
    assertThat(sync).contains("extra_rules_info")
    assertThat(sync).doesNotContain("cc_debug_info")

    val debug = INTELLIJ_CC_DEBUG_ASPECT.modules.map { it.file }
    assertThat(debug).containsExactly("cc_debug_info")
  }

  @Test
  fun testDebugModulesForRules() {
    assertThat(INTELLIJ_CC_DEBUG_ASPECT.modulesFor(setOf(Rules.CC)).map { it.file }).containsExactly("cc_debug_info")
    assertThat(INTELLIJ_CC_DEBUG_ASPECT.modulesFor(setOf(Rules.JAVA))).isEmpty()
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
    assertThat(content).doesNotContain("attr_aspects")
  }

  @Test
  fun testGenerateAspectFileAttrAspects() {
    val content = INTELLIJ_ASPECT.generateAspectFile(Path.of("aspect"), setOf(Rules.JAVA), listOf("deps", "exports"))
    assertThat(content).contains("""attr_aspects = ["deps", "exports"],""")

    val empty = INTELLIJ_ASPECT.generateAspectFile(Path.of("aspect"), setOf(Rules.JAVA), emptyList())
    assertThat(empty).contains("attr_aspects = [],")

    val default = INTELLIJ_ASPECT.generateAspectFile(Path.of("aspect"), setOf(Rules.JAVA), null)
    assertThat(default).doesNotContain("attr_aspects")
  }
}
