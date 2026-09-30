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
package com.intellij.aspect.testing.tests.sdk

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.lib.readTargetFromFile
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.io.path.writeBytes
import kotlin.io.path.writeText

@RunWith(JUnit4::class)
class ReadTargetTest {

  @Rule
  @JvmField
  val folder = TemporaryFolder()

  @Test
  fun testLargeUtf8FileAndUnknownFields() {
    val path = folder.newFile().toPath()
    val label = "//:café_工場_🧪"
    val option = "café_工場_🧪".repeat(10_000)
    path.writeText(
      """
      key { label: "$label" }
      kind: "java_library"
      java_common { javac_opts: "$option" }
      env { key: "MESSAGE" value: "line one\nline two\t\"quoted\"" }
      future_field { value: "unknown" }
      """.trimIndent(),
    )
    val errors = mutableListOf<String?>()

    val info = requireNotNull(readTargetFromFile(path, errors::add))

    assertThat(info.key.label).isEqualTo(label)
    assertThat(info.kind).isEqualTo("java_library")
    assertThat(info.javaCommon.javacOptsList).containsExactly(option)
    assertThat(info.envMap).containsExactly("MESSAGE", "line one\nline two\t\"quoted\"")
    assertThat(errors).isEmpty()
  }

  @Test
  fun testMalformedUtf8UsesReplacementCharacter() {
    val path = folder.newFile().toPath()
    path.writeBytes("kind: \"".toByteArray() + byteArrayOf(0xc3.toByte(), 0x28) + "\"".toByteArray())

    val info = requireNotNull(readTargetFromFile(path))

    assertThat(info.kind).isEqualTo("\uFFFD(")
  }

  @Test
  fun testMalformedTextReportsError() {
    val path = folder.newFile().toPath()
    path.writeText("key {")
    val errors = mutableListOf<String?>()

    assertThat(readTargetFromFile(path, errors::add)).isNull()
    assertThat(errors).hasSize(1)
    assertThat(errors.single()).isNotEmpty()
  }

  @Test
  fun testMissingFileReportsError() {
    val path = folder.root.toPath().resolve("missing.intellij-info.txt")
    val errors = mutableListOf<String?>()

    assertThat(readTargetFromFile(path, errors::add)).isNull()
    assertThat(errors).hasSize(1)
    assertThat(errors.single()).contains(path.fileName.toString())
  }
}
