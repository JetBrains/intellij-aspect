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

/**
 * Resolves a path string, expanding a leading `~` to the user's home directory.
 */
fun resolvePath(path: String): Path {
  if (path.startsWith("~/")) {
    return Path.of(System.getProperty("user.home")).resolve(path.removePrefix("~/"))
  }
  if (path == "~") {
    return Path.of(System.getProperty("user.home"))
  }
  return Path.of(path)
}

/**
 * Deletes a directory recursively, correctly handling symbolic links and junctions.
 */
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

        return FileVisitResult.CONTINUE
      }

      override fun visitFile(file: Path, attrs: BasicFileAttributes): FileVisitResult {
        Files.deleteIfExists(file)
        return FileVisitResult.CONTINUE
      }
    },
  )
}

fun asBazelPath(path: Path): String {
  return path.toString().replace('\\', '/').removeSuffix("/")
}
