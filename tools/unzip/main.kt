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

package com.intellij.aspect.tools.unzip

import com.intellij.aspect.private.lib.utils.unzip
import java.nio.file.Files
import java.nio.file.Path

/** Extracts a zip archive, optionally stripping leading path segments from all entries. */
fun main(args: Array<String>) {
  require(args.size in 2..3) { "usage: unzip <archive> <destination> [strip_prefix]" }

  val destination = Files.createDirectories(Path.of(args[1]))
  unzip(Path.of(args[0]), destination, stripPrefix = args.getOrNull(2)?.toInt() ?: 0)
}
