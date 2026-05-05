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
package com.intellij.aspect.testing.rules.fixture

import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.google.devtools.build.runfiles.Runfiles
import com.google.devtools.intellij.ideinfo.IdeInfo.*
import com.intellij.aspect.testing.rules.fixture.FixtureProto.TestFixture
import org.junit.AssumptionViolatedException
import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.FileInputStream
import java.io.IOException

private val RUNFILES = Runfiles.preload()

/**
 * JUnit resource for loading and accessing intellij aspect test fixtures.
 */
class AspectFixture : ExternalResource() {

  private lateinit var output: TestFixture

  override fun apply(base: Statement, description: Description): Statement {
    val files = System.getenv("ASPECT_FIXTURES").split(" ")

    return object : Statement() {
      override fun evaluate() {
        for (file in files) {
          output = loadAspectFixture(file)

          try {
            base.evaluate()
          } catch (e: AssertionError) {
            throw AssertionError("test failed in configuration: [${configString(output)}]", e)
          } catch (_: AssumptionViolatedException) {
            continue
          }
        }
      }
    }
  }

  fun findTargets(
    label: String,
    externalRepo: String? = null,
    fractionalAspectIds: List<String> = emptyList(),
  ): List<TargetIdeInfo> {
    return output.targetsList.filter { matchTarget(it, label, externalRepo, fractionalAspectIds) }
  }

  fun findTarget(
    label: String,
    externalRepo: String? = null,
    fractionalAspectIds: List<String> = emptyList(),
  ): TargetIdeInfo {
    val targets = findTargets(label, externalRepo, fractionalAspectIds)
    assertWithMessage("target not found: $label").that(targets).isNotEmpty()

    return targets.first()
  }

  fun findOutputGroup(
    group: String,
  ): List<String> {
    val groups = output.outputsList.filter { it.name == group }
    assertWithMessage("output group not found: $group").that(groups).isNotEmpty()

    return groups.first().filesList
  }

  fun findCIdeInfo(
    label: String,
    externalRepo: String? = null,
    fractionalAspectIds: List<String> = emptyList(),
  ): CIdeInfo {
    val target = findTarget(label, externalRepo, fractionalAspectIds)
    assertThat(target.hasCIdeInfo()).isTrue()

    return target.cIdeInfo
  }

  fun findPyIdeInfo(
    label: String,
    externalRepo: String? = null,
    fractionalAspectIds: List<String> = emptyList(),
  ): PyIdeInfo {
    val target = findTarget(label, externalRepo, fractionalAspectIds)
    assertThat(target.hasPyIdeInfo()).isTrue()

    return target.pyIdeInfo
  }

  fun bazelVersion(min: Int? = null, max: Int? = null): Boolean {
    val (major, _, _) = output.config.bazelVersion.split(".")
    if (min != null && min > major.toInt()) return false
    if (max != null && max < major.toInt()) return false

    return true
  }
}

@Throws(IOException::class)
private fun loadAspectFixture(file: String): TestFixture {
  val fixturePath = RUNFILES.unmapped().rlocation(file)

  FileInputStream(fixturePath).use { inputStream ->
    return TestFixture.parseFrom(inputStream)
  }
}

/**
 * Matches a target key, see [matchLabel] and [matchAspectIds] for details.
 */
private fun matchTarget(
  info: TargetIdeInfo,
  label: String,
  externalRepo: String?,
  fractionalAspectIds: List<String>,
): Boolean {
  return info.hasKey() &&
    matchLabel(info.key, label, externalRepo) &&
    matchAspectIds(info.key, fractionalAspectIds)
}

/**
 * Matches target key against a label. If the label is relative it is treated
 * as a test relative label. If a external repo is specified the label must be
 * absolute with regard to that repo.
 */
private fun matchLabel(key: TargetKey, label: String, externalRepo: String?): Boolean {
  if (externalRepo == null) {
    return key.label == label
  }
  if (!key.label.startsWith("@")) {
    return false
  }

  val (repo, relativeLabel) = key.label.split("//")
  val normalizeRepoName = repo.trimStart('@').replace("local_repository", "").replace("_repo_rules", "").trim('+', '~')

  return normalizeRepoName == externalRepo && "//$relativeLabel" == label
}

/**
 * Matches a target key against a list of partial target keys. Returns true if
 * any of the partial keys match or the list is empty.
 */
private fun matchAspectIds(key: TargetKey, fractionalAspectIds: List<String>): Boolean {
  if (fractionalAspectIds.isEmpty()) return true

  for (aspectId in key.aspectIdsList) {
    if (key.aspectIdsList.any { it.contains(aspectId) }) return true
  }

  return false
}

/**
 * Returns a string representation of the test configuration.
 */
private fun configString(fixture: TestFixture): String {
  val config = fixture.config

  return buildMap {
    put("bazel", config.bazelVersion)
    put("deploy", config.aspectDeployment.name.lowercase())

    config.modulesList.forEach { put(it.name, it.version) }

    if (fixture.extraFlagsList.isNotEmpty()) {
      put("flags", fixture.extraFlagsList.joinToString(","))
    }
  }.entries.joinToString(separator = ", ") { "${it.key}:${it.value}" }
}
