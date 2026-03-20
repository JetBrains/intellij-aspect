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
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.StandardCopyOption
import java.nio.file.attribute.BasicFileAttributes
import java.util.zip.ZipFile
import kotlin.io.path.ExperimentalPathApi

/**
 * Prefix directory for the aspect deploy locations.
 */
private val ASPECTS_DIRECTORY: Path = Path.of(".aspect")

/**
 * Configuration for a specific aspect (legacy or current).
 */
private data class Aspect(
  val deployDirectory: Path,
  val runfilesLocation: String,
  val aspectTargets: List<String>,
  val outputGroups: List<String>
)

private val CLWB_ASPECT = Aspect(
  deployDirectory = ASPECTS_DIRECTORY.resolve("clwb"),
  runfilesLocation = "tools/differ/clwb_aspect.zip",
  aspectTargets = listOf(":intellij_info_bundled.bzl%intellij_info_aspect"),
  outputGroups = listOf("intellij-info-generic", "intellij-info-cpp"),
)

private val CURRENT_ASPECT = Aspect(
  deployDirectory = ASPECTS_DIRECTORY.resolve("current"),
  runfilesLocation = "archive_ide.zip",
  aspectTargets = listOf(
    "/modules:cc_info.bzl%intellij_cc_info_aspect",
    "/intellij:aspect.bzl%intellij_info_aspect",
  ),
  outputGroups = listOf("intellij-info"),
)

/**
 * Temporary workspace that manages the .aspect directory lifecycle.
 * Automatically cleans up on close.
 */
class TemporaryWorkspace(private val workspace: Path, private val bazelExecutable: String) : AutoCloseable {

  /**
   * Extracts the CLwB aspect from the zip file and copies it into the
   * workspace.
   */
  @Throws(IOException::class)
  fun deployClwbAspect() {
    val archive = RunfilesRepo.rlocation(CLWB_ASPECT.runfilesLocation)

    val destination = workspace.resolve(CLWB_ASPECT.deployDirectory)
    Files.createDirectories(destination)

    ZipFile(archive.toFile()).use { zip ->
      zip.stream().forEach { entry ->
        val target = destination.resolve(entry.name)

        when {
          entry.isDirectory -> Files.createDirectories(target)
          else -> Files.copy(
            zip.getInputStream(entry),
            target,
            StandardCopyOption.REPLACE_EXISTING
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
  fun runClwbAspect(target: String): List<Path> = runAspect(CLWB_ASPECT, target)

  @Throws(IOException::class)
  fun runCurrentAspect(target: String): List<Path> = runAspect(CURRENT_ASPECT, target)

  @Throws(IOException::class)
  private fun runAspect(config: Aspect, target: String): List<Path> = executeBuild(
    workspaceRoot = workspace,
    bazelExecutable = bazelExecutable,
    outputGroups = config.outputGroups,
    aspects = config.aspectTargets.map { "//${config.deployDirectory}$it" },
    targets = listOf(target),
  )

  @OptIn(ExperimentalPathApi::class)
  override fun close() {
    val aspectsDirectory = workspace.resolve(ASPECTS_DIRECTORY)
    if (!Files.exists(aspectsDirectory)) return

    Files.walkFileTree(aspectsDirectory, object : SimpleFileVisitor<Path>() {
      override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
        Files.delete(file)
        return FileVisitResult.CONTINUE
      }

      override fun postVisitDirectory(dir: Path, exc: IOException?): FileVisitResult {
        Files.delete(dir)
        return FileVisitResult.CONTINUE
      }
    })
  }
}
