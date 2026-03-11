/*
 * Copyright 2025 JetBrains s.r.o. and contributors.
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

import com.google.devtools.intellij.ideinfo.IdeInfo.TargetIdeInfo
import com.google.protobuf.TextFormat
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import kotlin.system.exitProcess


fun main(args: Array<String>) {
  val parser = ArgParser("differ")

  val projectPath by parser.argument(
    ArgType.String,
    description = "Path to the Bazel project to analyze"
  )

  val bazelExecutable by parser.option(
    ArgType.String,
    shortName = "b",
    fullName = "bazel",
    description = "Path to bazel executable"
  ).default("bazel")

  val targetPattern by parser.option(
    ArgType.String,
    shortName = "t",
    fullName = "targets",
    description = "Target pattern to build"
  ).default("//...")

  val verbose by parser.option(
    ArgType.Boolean,
    shortName = "v",
    fullName = "verbose",
    description = "Show detailed progress and stack traces"
  ).default(false)

  val showFiltered by parser.option(
    ArgType.Boolean,
    shortName = "f",
    fullName = "show-filtered",
    description = "Show differences that were filtered by exception rules"
  ).default(false)

  parser.parse(args)

  try {
    System.err.println("Running differ on project: $projectPath")

    // set up the temporary workspace
    TemporaryWorkspace(Path.of(projectPath), bazelExecutable).use { workspace ->
      System.err.println("Deploying legacy aspect...")
      workspace.deployLegacyAspect()

      val legacyFiles = workspace.runLegacyAspect(targetPattern)
      val legacyTargets = loadTargets(legacyFiles)
      System.err.println("Legacy aspect generated: ${legacyFiles.size} files")

      System.err.println("Deploying current aspect...")
      workspace.deployCurrentAspect()

      val currentFiles = workspace.runCurrentAspect(targetPattern)
      val currentTargets = loadTargets(currentFiles)
      System.err.println("Current aspect generated: ${currentFiles.size} files")

      System.err.println("Comparing...")
      val rawResult = compareTargets(legacyTargets, currentTargets)

      // Apply exception filters to suppress known benign differences
      val filterResult = filterDifferences(rawResult.differences, DefaultFilters.ALL)
      System.err.println("Filtered ${filterResult.filtered.values.sumOf { it.size }} benign differences")

      // Create filtered comparison result
      val filteredComparison = rawResult.copy(differences = filterResult.kept)

      // Convert to JSON and output
      val jsonReport = filteredComparison.toJsonReport()
      println(serializeToJson(jsonReport))

      if (filteredComparison.differences.isEmpty() && filteredComparison.missing.isEmpty()) {
        exitProcess(0)
      } else {
        exitProcess(1)
      }
    }
  } catch (e: Exception) {
    System.err.println("Error: ${e.message}")
    if (verbose) {
      e.printStackTrace()
    }

    exitProcess(2)
  }
}

@Throws(IOException::class)
fun loadTargets(files: List<Path>): List<TargetIdeInfo> {
  return files.map { path ->
    Files.newInputStream(path).use { input ->
      val builder = TargetIdeInfo.newBuilder()
      TextFormat.Parser.newBuilder().build().merge(InputStreamReader(input, StandardCharsets.UTF_8), builder)
      builder.build()
    }
  }
}

