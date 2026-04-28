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

package com.intellij.aspect.testing.rules.worker

import com.intellij.aspect.private.lib.utils.isWindows
import com.intellij.aspect.private.lib.utils.parseBepOutputGroups
import com.intellij.aspect.private.lib.utils.unzip
import java.io.IOException
import java.io.OutputStream
import java.io.PrintStream
import java.lang.AutoCloseable
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import kotlin.collections.set
import kotlin.io.path.relativeTo

private const val PATH = "/usr/bin:/bin:/usr/local/bin"

class Sandbox(
  private val server: BazelServer,
  private val sandboxRoot: Path,
  private val stdout: OutputStream,
  private val stderr: OutputStream,
) : AutoCloseable {

  val projectDirectory: Path by lazy { tempDirectory("project") }

  val out: PrintStream = PrintStream(stdout)

  val err: PrintStream = PrintStream(stderr)

  @Throws(IOException::class)
  fun tempDirectory(name: String): Path {
    return Files.createDirectories(sandboxRoot.resolve(name)).toAbsolutePath()
  }

  @Throws(IOException::class)
  fun tempFile(name: String): Path {
    return sandboxRoot.resolve(name).toAbsolutePath()
  }

  @Throws(IOException::class)
  fun bazelBuild(
    version: String,
    targets: List<String>,
    aspects: List<String> = emptyList(),
    outputGroups: List<String> = emptyList(),
    flags: List<String> = emptyList(),
    profile: Path? = null,
  ): Map<String, Set<Path>> {
    val cmd = mutableListOf<String>()
    cmd.add(server.sharedResources.bazeliskBinary.toAbsolutePath().toString())
    cmd.add("--output_user_root=" + server.outputRootDirectory)
    cmd.add("--output_base=" + server.outputBaseDirectory)
    cmd.add("build")
    cmd.add("--disk_cache=" + server.sharedResources.diskCacheDirectory)
    cmd.add("--repository_cache=" + server.sharedResources.repoCacheDirectory)
    cmd.add("--registry=" + server.sharedResources.registryDirectory.toUri())

    if (aspects.isNotEmpty()) {
      cmd.add("--aspects=" + aspects.joinToString(","))
    }

    if (outputGroups.isNotEmpty()) {
      cmd.add("--output_groups=" + outputGroups.joinToString(","))
    }

    if (profile != null) {
      Files.createDirectories(profile.toAbsolutePath().parent)
      cmd.add("--profile=" + profile.toAbsolutePath())
    }

    val bepFile = tempFile("build.bep.json")
    cmd.add("--build_event_json_file=$bepFile")

    cmd.addAll(flags)
    cmd.addAll(targets)

    val process = ProcessBuilder(cmd)
      .directory(projectDirectory.toFile())
      .redirectErrorStream(true)
      .apply { environment().putAll(createEnvironment(version)) }
      .start()

    if (process.waitFor() != 0) {
      process.inputStream.transferTo(stderr)
      throw IOException("Bazel build failed: ${cmd.joinToString(" ")}")
    }

    return parseBepOutputGroups(bepFile)
  }

  private fun createEnvironment(version: String): Map<String, String> {
    val env = mutableMapOf<String, String>()
    env["PATH"] = PATH
    env["USE_BAZEL_VERSION"] = version
    env["BAZELISK_HOME"] = server.sharedResources.bazeliskHomeDirectory.toString()

    if (isWindows()) {
      env["BAZEL_SH"] = requireNotNull(System.getenv("BAZEL_SH"))
    }

    return env
  }

  @Throws(IOException::class)
  fun deployProject(archive: String) {
    unzip(Path.of(archive), projectDirectory)
  }

  fun relativeToOutputBase(absolute: Path): String = absolute.relativeTo(server.outputBaseDirectory).toString()

  override fun close() {
    err.close()
    out.close()
  }
}
