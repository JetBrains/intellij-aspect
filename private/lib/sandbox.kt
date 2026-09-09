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

package com.intellij.aspect.private.lib.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.relativeTo

private const val PATH = "/usr/bin:/bin:/usr/local/bin"

class Sandbox internal constructor(
  private val bazelisk: Path,
  val version: String,
  private val root: Path,
  private val logger: Logger,
) {

  private var outputRoot = root.resolve("output_root")
  private var outputBase = root.resolve("output_base")
  private var bazeliskHome = root.resolve("bazelisk_home")
  private var diskCache: Path? = null
  private var repoCache: Path? = null
  private var repoContentsCache: Path? = null
  private val registries = mutableListOf<Path>()

  val projectDirectory: Path by lazy { createDirectory("project") }

  /** Overwrites the default output root, e.g. to reuse a warm server outside the sandbox. */
  fun outputRoot(path: Path) {
    outputRoot = path
  }

  /** Overwrites the default output base, e.g. to reuse a warm server outside the sandbox. */
  fun outputBase(path: Path) {
    outputBase = path
  }

  /** Overwrites the default bazelisk home, e.g. to share downloaded bazel versions. */
  fun bazeliskHome(path: Path) {
    bazeliskHome = path
  }

  /** Uses [path] as the disk cache for all builds. */
  fun diskCache(path: Path) {
    diskCache = path
  }

  /** Uses [cache] as the repository cache, and [contents] as the bazel 8+ contents cache. */
  fun repoCache(cache: Path, contents: Path? = null) {
    repoCache = cache
    repoContentsCache = contents
  }

  /** Adds a local module registry, can be called repeatedly to add multiple registries. */
  fun registry(path: Path) {
    registries.add(path)
  }

  @Throws(IOException::class)
  fun createDirectory(name: String): Path {
    return Files.createDirectories(root.resolve(name)).toAbsolutePath()
  }

  @Throws(IOException::class)
  fun createFile(name: String): Path {
    return root.resolve(name).toAbsolutePath()
  }

  @Throws(IOException::class)
  suspend fun exec(action: String, args: List<String> = emptyList(), name: String? = null): String {
    val cmd = mutableListOf<String>()
    cmd.add(bazelisk.toAbsolutePath().toString())
    cmd.add("--nosystem_rc")
    cmd.add("--nohome_rc")
    cmd.add("--output_user_root=$outputRoot")
    cmd.add("--output_base=$outputBase")
    cmd.add(action)
    cmd.add("--lockfile_mode=update")

    diskCache?.let { cmd.add("--disk_cache=$it") }
    repoCache?.let { cmd.add("--repository_cache=$it") }

    if (majorVersion() >= 8) repoContentsCache?.let { cmd.add("--repo_contents_cache=$it") }
    for (registry in registries) cmd.add("--registry=" + registryUri(registry))

    cmd.addAll(args)

    return run(cmd, name)
  }

  fun close() {
    try {
      deleteRecursive(root)
    } catch (_: IOException) {
      // best effort cleanup
    }
  }

  fun relativeToOutputBase(absolute: Path): String {
    return asBazelPath(absolute.relativeTo(outputBase))
  }

  @Throws(IOException::class)
  private suspend fun run(cmd: List<String>, name: String? = null): String {
    val builder = ProcessBuilder(cmd)
    builder.directory(projectDirectory.toFile())

    // preserve the host environment, including TEMP/TMP required by Bazel's JNI loader on Windows
    builder.environment().putAll(environment())

    logger.log("$ " + cmd.joinToString(" "))

    val stdout = ByteArrayOutputStream()

    val exitCode = withContext(Dispatchers.IO) {
      val process = builder.start()

      launch {
        logger.stream(name).use(process.errorStream::transferTo)
      }
      launch {
        tee(stdout, logger.stream(name)).use(process.inputStream::transferTo)
      }

      process.waitFor()
    }

    if (exitCode != 0) {
      throw IOException("${cmd.joinToString(" ")} failed with code $exitCode")
    }

    return stdout.toString(Charsets.UTF_8)
  }

  private fun environment(): Map<String, String> {
    val env = mutableMapOf<String, String>()
    env["PATH"] = PATH
    env["USE_BAZEL_VERSION"] = version
    env["BAZELISK_HOME"] = Files.createDirectories(bazeliskHome).toString()
    env["BAZELISK_SKIP_WRAPPER"] = "true"

    if (isWindows()) {
      System.getenv("BAZEL_SH")?.let { env["BAZEL_SH"] = it }
    }

    return env
  }

  private fun majorVersion(): Int {
    return version.substringBefore('.').toIntOrNull() ?: 0
  }
}

/**
 * Creates a [Sandbox] rooted at [root] for the given bazel [version], applies
 * [body] to it, and closes it afterward.
 */
@Throws(IOException::class)
suspend fun <T> sandbox(
  bazelisk: Path,
  version: String,
  root: Path,
  logger: Logger,
  body: suspend Sandbox.() -> T,
): T {
  val sandbox = Sandbox(bazelisk, version, root, logger)

  try {
    return sandbox.body()
  } finally {
    sandbox.close()
  }
}

/**
 * Builds [targets] and returns the generated files grouped by output group.
 */
@Throws(IOException::class)
suspend fun Sandbox.build(
  targets: List<String>,
  aspects: List<String> = emptyList(),
  outputGroups: List<String> = emptyList(),
  flags: List<String> = emptyList(),
  profile: Path? = null,
  execLog: Path? = null,
): Map<String, Set<Path>> {
  val args = mutableListOf<String>()
  args.add("--norun_validations")

  if (aspects.isNotEmpty()) {
    args.add("--aspects=" + aspects.joinToString(","))
  }

  if (outputGroups.isNotEmpty()) {
    args.add("--output_groups=" + outputGroups.joinToString(","))
  }

  if (profile != null) {
    Files.createDirectories(profile.toAbsolutePath().parent)
    args.add("--profile=" + profile.toAbsolutePath())
  }

  if (execLog != null) {
    Files.createDirectories(execLog.toAbsolutePath().parent)
    args.add("--execution_log_compact_file=" + execLog.toAbsolutePath())
  }

  val bepFile = createFile("build.bep.json")
  args.add("--build_event_json_file=$bepFile")

  args.addAll(flags)
  args.addAll(targets)

  exec("build", args)

  return parseBepOutputGroups(bepFile)
}

@Throws(IOException::class)
suspend fun Sandbox.shutdown() {
  exec("shutdown")
}

/** Builds a file registry URI for bazel, workaround for Bazel 9 crash on Windows when host is null in file:// URIs */
private fun registryUri(registry: Path): String {
  val uri = registry.toUri()
  return URI(uri.scheme, "localhost", uri.path, uri.fragment).toString()
}
