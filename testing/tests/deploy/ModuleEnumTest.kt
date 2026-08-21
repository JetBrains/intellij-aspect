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
package com.intellij.aspect.testing.tests.deploy

import com.google.common.truth.Truth.assertWithMessage
import com.google.devtools.build.runfiles.Runfiles
import com.intellij.aspect.lib.Modules
import com.intellij.aspect.lib.Rules
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.zip.ZipFile

@RunWith(JUnit4::class)
class ModuleEnumTest {

  @Test
  fun testModuleFilesExist() {
    val archive = requireNotNull(System.getenv("ARCHIVE_IDE"))
    val archivePath = Runfiles.preload().unmapped().rlocation(archive)

    val entries = ZipFile(archivePath).use { zip ->
      zip.entries().asSequence().map { it.name }.toSet()
    }

    Modules.entries.forEach { module ->
      assertWithMessage("module %s declares missing file: %s", module.name, module.file)
        .that(entries)
        .contains("modules/${module.file}.bzl")
    }
  }
}
