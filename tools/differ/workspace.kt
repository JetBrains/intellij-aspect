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
package com.intellij.aspect.tools.differ

import com.intellij.aspect.lib.AspectConfig
import com.intellij.aspect.lib.deployAspectZip
import com.intellij.aspect.tools.RunfilesRepo
import com.intellij.aspect.tools.lib.executeBuild
import com.intellij.aspect.tools.lib.executeCommand
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.zip.ZipFile
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively

/**
 * Prefix directory for the aspect deploy locations.
 */
private val ASPECTS_DIRECTORY: Path = Path.of(".aspect")

/**
 * Configuration for a specific aspect (legacy or current).
 */
private data class Aspect(
  val deployDirectory: Path,
  val runfilesLocation: String?,
  val aspectTargets: List<String>,
  val outputGroups: List<String>,
)

data class AspectOverride(
  val deployDirectory: Path? = null,
  val aspectTargets: List<String>? = null,
  val outputGroups: List<String>? = null,
)

private fun Aspect.overrideWith(override: AspectOverride): Aspect {
  return Aspect(
    deployDirectory = override.deployDirectory ?: deployDirectory,
    runfilesLocation = runfilesLocation,
    aspectTargets = override.aspectTargets ?: aspectTargets,
    outputGroups = override.outputGroups ?: outputGroups,
  )
}

private val REFERENCE_ASPECT = Aspect(
  deployDirectory = ASPECTS_DIRECTORY.resolve("reference"),
  runfilesLocation = null,
  aspectTargets = listOf(),
  outputGroups = listOf(),
)

private val CURRENT_ASPECT = Aspect(
  deployDirectory = ASPECTS_DIRECTORY.resolve("current"),
  runfilesLocation = "archive_ide.zip",
  aspectTargets = listOf(
    "intellij:aspect.bzl%intellij_info_aspect",
  ),
  outputGroups = listOf("intellij-info"),
)

/**
 * Temporary workspace that manages the .aspect directory lifecycle.
 * Automatically cleans up on close.
 */
class TemporaryWorkspace(private val workspace: Path, private val bazelExecutable: String) : AutoCloseable {

  /**
   * Extracts the aspect from the zip file and copies it into the
   * workspace.
   */
  @Throws(IOException::class)
  fun deployReferenceAspect(
    zipFile: String? = null,
    deployDirectory: Path? = null,
  ) {
    val archive =
      zipFile?.let { Path.of(it) } ?: RunfilesRepo.rlocation(REFERENCE_ASPECT.runfilesLocation)
        ?: throw IllegalStateException("Reference aspect zip file has to be specified")

    val destination = workspace.resolve(deployDirectory ?: REFERENCE_ASPECT.deployDirectory)
    Files.createDirectories(destination)

    ZipFile(archive.toFile()).use { zip ->
      zip.stream().forEach { entry ->
        val target = destination.resolve(entry.name)

        when {
          entry.isDirectory -> Files.createDirectories(target)

          else -> Files.copy(
            zip.getInputStream(entry),
            target,
            StandardCopyOption.REPLACE_EXISTING,
          )
        }
      }
    }
  }

  /**
   * Uses the provided deployment infrastructure for the aspect to copy it into
   * the workspace and generate the configuration.
   */
  @Throws(IOException::class)
  fun deployCurrentAspect() {
    val version = executeCommand(bazelExecutable, "--version").removePrefix("bazel").trim()
    val config = AspectConfig(
      bazelVersion = version,
      repoMapping = emptyMap(),
      useBuiltin = false,
    )

    val archive = RunfilesRepo.rlocation(CURRENT_ASPECT.runfilesLocation)
    deployAspectZip(
      workspace,
      CURRENT_ASPECT.deployDirectory,
      archive,
      config,
    )
  }

  @Throws(IOException::class)
  fun runReferenceAspect(
    target: String,
    override: AspectOverride,
    verbose: Boolean,
  ): List<Path> = runAspect(REFERENCE_ASPECT.overrideWith(override), target, verbose)

  @Throws(IOException::class)
  fun runCurrentAspect(
    target: String,
    override: AspectOverride,
    verbose: Boolean,
  ): List<Path> = runAspect(CURRENT_ASPECT.overrideWith(override), target, verbose)

  @Throws(IOException::class)
  private fun runAspect(config: Aspect, target: String, verbose: Boolean): List<Path> = executeBuild(
    workspaceRoot = workspace,
    bazelExecutable = bazelExecutable,
    outputGroups = config.outputGroups,
    aspects = config.aspectTargets.map { "//${config.deployDirectory}/$it" },
    targets = listOf(target),
    verbose = verbose,
  )

  @OptIn(ExperimentalPathApi::class)
  override fun close() {
    val aspectsDirectory = workspace.resolve(ASPECTS_DIRECTORY)
    if (!Files.exists(aspectsDirectory)) return

    aspectsDirectory.deleteRecursively()
  }
}
