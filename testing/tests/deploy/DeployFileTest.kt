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
package com.intellij.aspect.testing.tests.lib

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.lib.writeFileIfContentDiffers
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.nio.file.Path
import java.nio.file.attribute.FileTime
import kotlin.io.path.createDirectories
import kotlin.io.path.getLastModifiedTime
import kotlin.io.path.isRegularFile
import kotlin.io.path.readText
import kotlin.io.path.setLastModifiedTime
import kotlin.io.path.writeText

@RunWith(JUnit4::class)
class DeployFileTest {

  @Test
  fun testDeployPlain() {
    val tempdir = requireNotNull(System.getenv("TEST_TMPDIR"))

    val location = Path.of(tempdir, "module.bzl")
    val payload = "# Just an empty placeholder"

    writeFileIfContentDiffers(location, payload)
    assertThat(location.isRegularFile()).isTrue()
    assertThat(location.readText()).isEqualTo(payload)
  }

  @Test
  fun testFileInTheWay() {
    val tempdir = requireNotNull(System.getenv("TEST_TMPDIR"))

    val location = Path.of(tempdir, "module.bzl")

    location.writeText("# Some other content")

    val payload = "# Just an empty placeholder"

    writeFileIfContentDiffers(location, payload)
    assertThat(location.isRegularFile()).isTrue()
    assertThat(location.readText()).isEqualTo(payload)
  }

  @Test
  fun testDirectoryInTheWay() {
    val tempdir = requireNotNull(System.getenv("TEST_TMPDIR"))

    val location = Path.of(tempdir, "BUILD")

    location.resolve("subdir").createDirectories()
    location.resolve("sample_file").writeText("some file")

    val payload = "# Just an empty placeholder"
    writeFileIfContentDiffers(location, payload)
    assertThat(location.isRegularFile()).isTrue()
    assertThat(location.readText()).isEqualTo(payload)
  }

  @Test
  fun fileNotChanged() {
    val tempdir = requireNotNull(System.getenv("TEST_TMPDIR"))

    val location = Path.of(tempdir, "module.bzl")
    val payload = "# Just an empty placeholder"
    location.writeText(payload)
    val dateInThePast = FileTime.fromMillis(1577836800) // Jan 1, 2020

    location.setLastModifiedTime(dateInThePast)

    writeFileIfContentDiffers(location, payload)
    assertThat(location.getLastModifiedTime()).isEqualTo(dateInThePast)
  }
}
