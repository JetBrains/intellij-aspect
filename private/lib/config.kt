/*
 * Copyright 2026 The Bazel Authors. All rights reserved.
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
package com.intellij.aspect.lib

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path

/**
 * Creates the config directory and writes the config file as well as the
 * required BUILD file.
 */
@Throws(IOException::class)
fun writeAspectConfig(destination: Path, config: AspectConfig) {
  val directory = destination.resolve("config")
  Files.createDirectories(directory)

  val buildFile = directory.resolve("BUILD")
  Files.writeString(buildFile, "# generated build file", Charsets.UTF_8)

  val configFile = directory.resolve("config.bzl")
  Files.writeString(configFile, generateConfigStruct(config), Charsets.UTF_8)
}

private fun generateConfigStruct(config: AspectConfig) = """
# generated config file by deployment

config = struct(
	bazel_version = "${config.bazelVersion}",
)
"""
