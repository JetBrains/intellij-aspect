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
import com.google.devtools.build.runfiles.Runfiles
import com.intellij.aspect.lib.AspectConfig
import com.intellij.aspect.lib.LoadStatement
import com.intellij.aspect.lib.Repository
import com.intellij.aspect.lib.Rules
import com.intellij.aspect.lib.deployAspectZip
import com.intellij.aspect.lib.parseLoads
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
        rulesets = Rules.entries.toSet(),
      ),
    )

    val repos = readLoads(path, "modules/cc_info.bzl").map { it.repository }
    assertThat(repos).contains(Repository.External("@rules_cc"))
    assertThat(repos).contains(Repository.Absolute)

    val aspectLoads = readLoads(path, "config/aspect.bzl")
    assertThat(aspectLoads).contains(
      LoadStatement(Repository.Absolute, "aspect/location/intellij:aspect.bzl", "\"intellij_configure_aspect\""),
    )
    assertThat(aspectLoads.map { it.path }).contains("aspect/location/modules:cc_info.bzl")
    assertThat(aspectLoads.map { it.path }).doesNotContain("aspect/location/modules:cc_debug_info.bzl")

    val debugAspectLoads = readLoads(path, "config/cc_debug_aspect.bzl")
    assertThat(debugAspectLoads.map { it.path }).contains("aspect/location/modules:cc_debug_info.bzl")
    assertThat(debugAspectLoads.map { it.path }).doesNotContain("aspect/location/modules:cc_info.bzl")
  }

  @Test
  fun testDeployDebugAspectWithoutCc() {
    val path = deployArchive(
      AspectConfig(
        bazelVersion = "8.5.0",
        repoMapping = emptyMap(),
        useBuiltin = emptySet(),
        rulesets = setOf(Rules.JAVA),
      ),
    )

    // the debug aspect is always deployed, even if it has no modules
    val content = Files.readString(path.resolve("config/cc_debug_aspect.bzl"))
    assertThat(content).contains("intellij_cc_debug_aspect = intellij_configure_aspect(")
    assertThat(content).doesNotContain("cc_debug_info")
  }

  @Test
  fun testDeployBuiltinRules() {
    val path = deployArchive(
      AspectConfig(
        bazelVersion = "8.5.0",
        repoMapping = emptyMap(),
        useBuiltin = Rules.entries.toSet(),
        rulesets = Rules.entries.toSet(),
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
        rulesets = Rules.entries.toSet(),
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
        repoMapping = mapOf(Rules.CC to "@my_rules_cc"),
        useBuiltin = emptySet(),
        rulesets = setOf(Rules.CC),
      ),
    )

    val repos = readLoads(path, "modules/cc_info.bzl").map { it.repository }
    assertThat(repos).contains(Repository.External("@my_rules_cc"))
    assertThat(repos).doesNotContain(Repository.External("@rules_cc"))
  }

  @Test
  fun testDeployPlatformsDefault() {
    val path = deployArchive(
      AspectConfig(
        bazelVersion = "8.5.0",
        repoMapping = emptyMap(),
        useBuiltin = emptySet(),
        rulesets = setOf(Rules.CC, Rules.PLATFORMS),
      ),
    )

    val content = Files.readString(path.resolve("modules/platform_info.bzl"))
    assertThat(content).contains("@platforms//os:linux")
    assertThat(content).contains("@platforms//cpu:x86_64")

    val debugAspectLoads = readLoads(path, "config/cc_debug_aspect.bzl")
    assertThat(debugAspectLoads.map { it.path }).contains("aspect/location/modules:platform_info.bzl")
  }

  @Test
  fun testDeployPlatformsRepoMapping() {
    val path = deployArchive(
      AspectConfig(
        bazelVersion = "8.5.0",
        repoMapping = mapOf(Rules.PLATFORMS to "@@platforms+"),
        useBuiltin = emptySet(),
        rulesets = setOf(Rules.PLATFORMS),
      ),
    )

    val content = Files.readString(path.resolve("modules/platform_info.bzl"))
    assertThat(content).contains("@@platforms+//os:macos")
    assertThat(content).contains("@@platforms+//cpu:aarch64")
    assertThat(content).doesNotContain("\"@platforms//")
  }

  @Test
  fun testDeployPlatformsBuiltin() {
    val path = deployArchive(
      AspectConfig(
        bazelVersion = "8.5.0",
        repoMapping = emptyMap(),
        useBuiltin = setOf(Rules.PLATFORMS),
        rulesets = setOf(Rules.PLATFORMS),
      ),
    )

    val content = Files.readString(path.resolve("modules/platform_info.bzl"))
    assertThat(content).contains("@@platforms//os:linux")
    assertThat(content).doesNotContain("\"@platforms//")
  }
}
