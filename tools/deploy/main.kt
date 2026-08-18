/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.aspect.tools.deploy

import com.intellij.aspect.lib.AspectConfig
import com.intellij.aspect.lib.Rules
import com.intellij.aspect.lib.deployAspectZip
import com.intellij.aspect.tools.RunfilesRepo
import com.intellij.aspect.tools.lib.executeCommand
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.required
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.system.exitProcess

private const val ARCHIVE_IDE = "archive_ide.zip"
private const val LOCAL_DEPLOY = "archive_bcr.tar.gz"

fun main(args: Array<String>) {
  val parser = ArgParser("deploy")

  val method by parser.argument(
    ArgType.String,
    description = "Deployment method: bcr, materialized, or builtin",
  )

  val path by parser.argument(
    ArgType.String,
    description = "Target directory path",
  )

  val bazelVersion by parser.option(
    ArgType.String,
    shortName = "b",
    fullName = "bazel_version",
    description = "The Bazel version to deploy",
  ).default("9.2.0")

  val verbose by parser.option(
    ArgType.Boolean,
    shortName = "v",
    fullName = "verbose",
    description = "Show detailed progress and stack traces",
  ).default(false)

  val relativeDestination by parser.option(
    ArgType.String,
    fullName = "relative_path",
    description = "Relative path inside the project",
  )

  val languages by parser.option(
    ArgType.String,
    shortName = "l",
    fullName = "languages",
    description = "Comma separated list of languages to deploy",
  ).required()

  val ruleRemap by parser.option(
    ArgType.String,
    fullName = "rule_remap",
    description = "Comma separated ruleset repo remappings, e.g. scala=@my_scala,cc=@my_rules_cc",
  ).default("")

  parser.parse(args)

  val targetPath = Path.of(path).toAbsolutePath()
  val relativePath = relativeDestination?.let(Path::of)

  val rulesets = languages.split(",").mapNotNull { language ->
    Rules.entries.firstOrNull { it.name.equals(language, ignoreCase = true) }
  }.toSet()

  System.err.println("Selected languages: ${rulesets.joinToString(", ")}")

  val repoMapping = parseRuleRemap(ruleRemap)

  if (repoMapping.isNotEmpty()) {
    System.err.println("Rule remappings: ${repoMapping.entries.joinToString(", ") { "${it.key}=${it.value}" }}")
  }

  try {
    when (method) {
      "bcr" -> deployBcr(targetPath)
      "materialized" -> deployIde(targetPath, relativePath, rulesets, repoMapping, bazelVersion, useBuiltin = false)
      "builtin" -> deployIde(targetPath, relativePath, rulesets, repoMapping, bazelVersion, useBuiltin = true)
    }

    System.err.println("Deployed aspect ($method) to $targetPath")
  } catch (e: Exception) {
    System.err.println("Error: ${e.message}")
    if (verbose) {
      e.printStackTrace()
    }

    exitProcess(2)
  }
}

@Throws(IOException::class)
private fun deployBcr(targetPath: Path) {
  Files.createDirectories(targetPath)

  executeCommand(
    "tar",
    "xf", RunfilesRepo.rlocation(LOCAL_DEPLOY).toString(),
    "-C", targetPath.toString(),
    "--strip-components", "1",
  )
}

/**
 * Parses a comma separated list of `ruleset=@repo` remappings into a [Rules] keyed map.
 *
 * Ruleset keys are matched against the [Rules] enum case-insensitively. Exits the process with a
 * clear message on a malformed entry or an unknown ruleset key.
 */
private fun parseRuleRemap(remap: String): Map<Rules, String> {
  return remap.split(",").filter { it.isNotBlank() }.associate { entry ->
    val separator = entry.indexOf('=')
    if (separator < 0) {
      System.err.println("Error: invalid rule remapping '${entry.trim()}', expected ruleset=@repo")
      exitProcess(2)
    }

    val key = entry.substring(0, separator).trim()
    val value = entry.substring(separator + 1).trim()

    val rule = Rules.entries.firstOrNull { it.name.equals(key, ignoreCase = true) }
    if (rule == null) {
      System.err.println("Error: unknown ruleset '$key' in rule remapping")
      exitProcess(2)
    }

    rule to value
  }
}

@Throws(IOException::class)
private fun deployIde(
  targetPath: Path,
  relativePath: Path?,
  rulesets: Set<Rules>,
  repoMapping: Map<Rules, String>,
  bazelVersion: String,
  useBuiltin: Boolean,
) {
  val config = AspectConfig(
    bazelVersion = bazelVersion,
    repoMapping = repoMapping,
    useBuiltin = if (useBuiltin) Rules.entries.toSet() else emptySet(),
    rulesets = rulesets,
  )

  deployAspectZip(
    workspaceRoot = targetPath,
    relativeDestination = relativePath ?: Path.of("aspect", if (useBuiltin) "builtin" else "default"),
    archiveZip = RunfilesRepo.rlocation(ARCHIVE_IDE),
    config = config,
  )
}
