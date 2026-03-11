/*
 * Copyright 2026 JetBrains s.r.o. and contributors.
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

package com.intellij.aspect.testing.rules.lib

import com.google.protobuf.Message
import com.google.protobuf.TextFormat
import com.intellij.aspect.private.lib.parseBepFile
import com.intellij.aspect.testing.rules.lib.ActionLibProto.BazelModule
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

@Throws(IOException::class)
inline fun <reified T : Message> action(args: Array<String>, crossinline block: ActionContext.(T) -> Unit) {
  val builder = T::class.java.getMethod("newBuilder").invoke(null) as Message.Builder
  TextFormat.Parser.newBuilder().build().merge(args[0], builder)

  block(ActionContext(), builder.build() as T)
}

class ActionContext {

  val projectDirectory: Path by lazy { tempDirectory("project") }

  val repoCacheDirectory: Path by lazy { tempDirectory("repo_cache") }

  val outputRootDirectory: Path by lazy { tempDirectory("output_root") }

  val registryDirectory: Path by lazy { tempDirectory("registry") }

  @Throws(IOException::class)
  fun tempDirectory(name: String): Path {
    return Files.createDirectories(Path.of(name)).toAbsolutePath()
  }

  @Throws(IOException::class)
  fun tempFile(name: String): Path {
    return tempDirectory("temp").resolve(name)
  }

  @Throws(IOException::class)
  fun deployProject(project: String) {
    unzip(Path.of(project), projectDirectory)
  }

  @Throws(IOException::class)
  fun deployRegistry(registry: String) {
    unzip(Path.of(registry), registryDirectory, stripPrefix = 1)
  }

  @Throws(IOException::class)
  fun deployRepoCache(archive: String) {
    val addressable = repoCacheDirectory.resolve("content_addressable")
    Files.createDirectories(addressable)
    unzip(Path.of(archive), addressable)
  }

  @Throws(IOException::class)
  fun archiveRepoCache(archive: String) {
    val addressable = repoCacheDirectory.resolve("content_addressable")
    zip(addressable, Path.of(archive))
  }

  @Throws(IOException::class)
  fun writeModule(modules: List<BazelModule>, aspect: Path? = null) {
    val writer = Files.newOutputStream(
      projectDirectory.resolve("MODULE.bazel"),
      StandardOpenOption.CREATE,
      StandardOpenOption.TRUNCATE_EXISTING,
    ).bufferedWriter()

    writer.use {
      for (module in modules) {
        it.appendLine("bazel_dep(name = '${module.name}', version = '${module.version}')")
      }

      if (aspect != null) {
        it.appendLine("bazel_dep(name = 'intellij_aspect')")
        it.appendLine("local_path_override(module_name = 'intellij_aspect', path = '$aspect')")
      }
    }
  }

  @Throws(IOException::class)
  fun bazelBuild(
    bazel: ActionLibProto.BazelBinary,
    targets: List<String>,
    aspects: List<String> = emptyList(),
    outputGroups: List<String> = emptyList(),
    flags: List<String> = emptyList(),
  ): List<Path> {
    val cmd = mutableListOf<String>()
    cmd.add(Path.of(bazel.executable).toAbsolutePath().toString())
    cmd.add("--output_user_root=$outputRootDirectory")
    cmd.add("build")
    cmd.add("--repository_cache=$repoCacheDirectory")
    cmd.add("--registry=file://$registryDirectory")

    val bepFile = tempFile("build.bep.json")
    cmd.add("--build_event_json_file=$bepFile")

    if (aspects.isNotEmpty()) {
      cmd.add("--aspects=" + aspects.joinToString(","))
    }

    if (outputGroups.isNotEmpty()) {
      cmd.add("--output_groups=" + outputGroups.joinToString(","))
    }

    cmd.addAll(flags)
    cmd.addAll(targets)

    val process = ProcessBuilder(cmd)
      .directory(projectDirectory.toFile())
      .start()

    val exitCode = process.waitFor()
    if (exitCode != 0) {
      process.errorStream.transferTo(System.err)
      throw IOException("Command failed: ${cmd.joinToString(" ")}")
    }

    return parseBepFile(bepFile)
  }
}
