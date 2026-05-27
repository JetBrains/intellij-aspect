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
package com.intellij.aspect.testing.tests.lib

import com.google.common.truth.Truth.assertThat
import com.google.devtools.build.runfiles.Runfiles
import com.intellij.aspect.lib.AspectConfig
import com.intellij.aspect.lib.LoadStatement
import com.intellij.aspect.lib.Repository
import com.intellij.aspect.lib.Rules
import com.intellij.aspect.lib.deployAspectZip
import com.intellij.aspect.lib.parseLoads
import com.intellij.aspect.lib.repoMappingForRules
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

private val RUNFILES = Runfiles.preload()

@RunWith(JUnit4::class)
class DeployTest {

  @Throws(IOException::class)
  private fun deployArchive(config: AspectConfig): Path {
    val archive = requireNotNull(System.getenv("ARCHIVE_IDE"))
    val tempdir = requireNotNull(System.getenv("TEST_TMPDIR"))

    val relativeDestination = Path.of("aspect").resolve("location")

    deployAspectZip(
      workspaceRoot = Path.of(tempdir),
      relativeDestination = relativeDestination,
      archiveZip = Path.of(RUNFILES.unmapped().rlocation(archive)),
      config = config,
    )

    return Path.of(tempdir).resolve(relativeDestination)
  }

  private fun readLoads(root: Path, file: String): List<LoadStatement> {
    return Files.newInputStream(root.resolve(file)).use(::parseLoads)
  }

  @Test
  fun testDeployDefault() {
    val path = deployArchive(
      AspectConfig(
        bazelVersion = "8.5.0",
        repoMapping = emptyMap(),
        useBuiltin = emptySet(),
      ),
    )

    val repos = readLoads(path, "modules/cc_info.bzl").map { it.repository }
    assertThat(repos).contains(Repository.External("@rules_cc"))
    assertThat(repos).contains(Repository.Absolute)
  }

  @Test
  fun testDeployBuiltinRules() {
    val path = deployArchive(
      AspectConfig(
        bazelVersion = "8.5.0",
        repoMapping = emptyMap(),
        useBuiltin = Rules.entries.toSet(),
      ),
    )

    val repos = readLoads(path, "modules/cc_info.bzl").map { it.repository }
    assertThat(repos).doesNotContain(Repository.External("@rules_cc"))

    val content = Files.readString(path.resolve("modules/cc_info.bzl"))
    assertThat(content).contains("CC_TOOLCHAIN_TYPE = Label(\"@bazel_tools//tools/cpp:toolchain_type\")")
  }

  @Test
  fun testDeployPartialBuiltinRules() {
    val path = deployArchive(
      AspectConfig(
        bazelVersion = "8.5.0",
        repoMapping = emptyMap(),
        useBuiltin = setOf(Rules.PYTHON),
      ),
    )

    val reposCC = readLoads(path, "modules/cc_info.bzl").map { it.repository }
    assertThat(reposCC).contains(Repository.External("@rules_cc"))
    assertThat(reposCC).contains(Repository.Absolute)

    val reposPython = readLoads(path, "modules/python_info.bzl").map { it.repository }
    assertThat(reposPython).doesNotContain(Repository.External("@rules_python"))
  }

  @Test
  fun testDeployRepoMapping() {
    val path = deployArchive(
      AspectConfig(
        bazelVersion = "8.5.0",
        repoMapping = mapOf("@rules_cc" to "@my_rules_cc"),
        useBuiltin = emptySet(),
      ),
    )

    val repos = readLoads(path, "modules/cc_info.bzl").map { it.repository }
    assertThat(repos).contains(Repository.External("@my_rules_cc"))
    assertThat(repos).doesNotContain(Repository.External("@rules_cc"))
  }

  @Test
  fun testLanguageRepoMapping() {
    assertThat(
      repoMappingForRules(
        mapOf(
          Rules.PYTHON to "@@rules_python+",
          Rules.PROTO to "@com_google_protobuf",
        ),
      ),
    ).containsExactly(
      "@rules_python", "@@rules_python+",
      "@protobuf", "@com_google_protobuf",
    )

    // Also verify that all languages are covered.
    for (language in Rules.entries) {
      assertThat(repoMappingForRules(mapOf(language to "@@canonical+")).values).contains("@@canonical+")
    }
  }
}
