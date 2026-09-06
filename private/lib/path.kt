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

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

/** Resolves a path string, expanding a leading `~` to the user's home directory. */
fun resolvePath(path: String): Path {
  if (path.startsWith("~/")) {
    return Path.of(System.getProperty("user.home")).resolve(path.removePrefix("~/"))
  }
  if (path == "~") {
    return Path.of(System.getProperty("user.home"))
  }
  return Path.of(path)
}

/** Resolves the temp directory that should be used in the current context. */
fun resolveTempDirectory(): Path {
  val options = sequence<String?> {
    yield(System.getenv("TEST_TMPDIR"))
    yield(System.getenv("TMPDIR"))
    yield(System.getProperty("java.io.tmpdir"))

    yield(".") // fallback to the current execution root
  }

  return options.filterNotNull().filter { it.isNotBlank() }.map(Path::of).first()
}

/** Creates a new temp directory with the given prefix. */
@Throws(IOException::class)
fun createTempDirectory(prefix: String): Path {
  return Files.createTempDirectory(resolveTempDirectory(), prefix)
}

/** Deletes a directory recursively, correctly handling symbolic links and junctions. */
@Throws(IOException::class)
fun deleteRecursive(directory: Path) {
  Files.walkFileTree(
    directory,
    object : SimpleFileVisitor<Path>() {
      override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
        if (attrs.isSymbolicLink || attrs.isOther || !attrs.isDirectory) {
          Files.deleteIfExists(dir) // remove the symlink or junction
          return FileVisitResult.SKIP_SUBTREE
        }

        // fetched repositories may be write protected, e.g. hermetic toolchains
        if (!Files.isWritable(dir)) {
          dir.toFile().setWritable(true)
        }

        return FileVisitResult.CONTINUE
      }

      override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
        if (!Files.isWritable(file)) {
          file.toFile().setWritable(true)
        }

        Files.deleteIfExists(file)
        return FileVisitResult.CONTINUE
      }

      override fun postVisitDirectory(dir: Path, exc: IOException?): FileVisitResult {
        Files.deleteIfExists(dir)
        return FileVisitResult.CONTINUE
      }
    },
  )
}

fun asBazelPath(path: Path): String {
  return path.toString().replace('\\', '/').removeSuffix("/")
}
