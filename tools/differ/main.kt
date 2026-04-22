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

import com.google.devtools.intellij.ideinfo.IdeInfo.TargetIdeInfo
import com.google.protobuf.TextFormat
import com.intellij.aspect.lib.Languages
import com.intellij.aspect.lib.aspectsForLanguages
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
    description = "Path to the Bazel project to analyze",
  )

  val bazelExecutable by parser.option(
    ArgType.String,
    shortName = "b",
    fullName = "bazel",
    description = "Path to bazel executable",
  ).default("bazel")

  val targetPattern by parser.option(
    ArgType.String,
    shortName = "t",
    fullName = "targets",
    description = "Target pattern to build",
  ).default("//...")

  val verbose by parser.option(
    ArgType.Boolean,
    shortName = "v",
    fullName = "verbose",
    description = "Show detailed progress and stack traces",
  ).default(false)

  val referenceZipFile by parser.option(
    ArgType.String,
    shortName = "z",
    fullName = "zipfile",
    description = "Path to the zip file to compare the aspect against",
  )

  val deployDirectory by parser.option(
    ArgType.String,
    shortName = "d",
    fullName = "deploy",
    description = "Path to the directory to deploy the aspect to",
  )

  val referenceAspects by parser.option(
    ArgType.String,
    shortName = "a",
    fullName = "aspects",
    description = "Aspects to include in the reference aspect",
  )

  val referenceOutputGroups by parser.option(
    ArgType.String,
    shortName = "g",
    fullName = "groups",
    description = "Output groups to include in the reference aspect",
  )

  val deployLanguages by parser.option(
    ArgType.String,
    shortName = "l",
    fullName = "languages",
    description = "Languages to deploy the current aspect for",
  )

  parser.parse(args)

  try {
    val currentAspectsToRun = deployLanguages?.let { parseLanguages(it) }?.let { aspectsForLanguages(it.toSet()) }
    System.err.println("Running differ on project: $projectPath")

    // set up the temporary workspace
    TemporaryWorkspace(Path.of(projectPath), bazelExecutable).use { workspace ->
      System.err.println("Deploying reference aspect...")
      workspace.deployReferenceAspect(
        zipFile = referenceZipFile,
        deployDirectory = deployDirectory?.let {
          Path.of(it)
        },
      )

      val referenceFiles = workspace.runReferenceAspect(
        targetPattern,
        AspectOverride(
          deployDirectory = deployDirectory?.let {
            Path.of(it)
          },
          aspectTargets = referenceAspects?.split(","),
          outputGroups = referenceOutputGroups?.split(","),
        ),
      )
      val referenceTargets = loadTargets(referenceFiles).map { normalizeTargetKeyLabel(it) }
      System.err.println("Reference aspect generated: ${referenceFiles.size} files")

      System.err.println("Deploying current aspect...")
      workspace.deployCurrentAspect()

      val currentFiles = workspace.runCurrentAspect(targetPattern, AspectOverride(aspectTargets = currentAspectsToRun))
      val currentTargets = loadTargets(currentFiles)
      System.err.println("Current aspect generated: ${currentFiles.size} files")

      System.err.println("Comparing...")
      val rawResult = compareTargets(referenceTargets, currentTargets)

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

// Normalize target key label to match the way our aspect represents targets
// (without qualified name for the main repository).
fun normalizeTargetKeyLabel(target: TargetIdeInfo): TargetIdeInfo {
  return target.toBuilder()
    .setKey(
      target.key.toBuilder().setLabel(
        if (target.key.label.startsWith("@@//") ||
          target.key.label.startsWith("@//")
        ) {
          target.key.label.trimStart { it == '@' }
        } else {
          target.key.label
        },
      ).build(),
    )
    .build()
}

private fun stringToLangue(s: String): Languages {
  return when (s) {
    "cc" -> Languages.CC
    "java" -> Languages.JAVA
    "kotlin" -> Languages.KOTLIN
    "proto" -> Languages.PROTO
    else -> throw IllegalArgumentException("Unknown language: $s")
  }
}

fun parseLanguages(s: String) = s.split(",").map(::stringToLangue)
