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
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.TargetIdeInfo
import com.google.protobuf.TextFormat
import com.intellij.aspect.lib.readTargetFromFile
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.nio.file.Path
import kotlin.io.path.reader
import kotlin.io.path.writeBytes
import kotlin.io.path.writeText

@RunWith(JUnit4::class)
class ReadTargetTest {

  @Rule
  @JvmField
  val folder = TemporaryFolder()

  @Test
  fun testReadsLargeUtf8FileLikeReader() {
    val path = folder.newFile().toPath()
    val kind = "café_工場_🧪".repeat(2_000)
    path.writeText("kind: \"$kind\"")

    assertThat(readTargetFromFile(path)).isEqualTo(readTargetWithReader(path))
  }

  @Test
  fun testReplacesMalformedUtf8LikeReader() {
    val path = folder.newFile().toPath()
    path.writeBytes("kind: \"".toByteArray() + byteArrayOf(0xc3.toByte(), 0x28) + "\"".toByteArray())

    assertThat(readTargetFromFile(path)).isEqualTo(readTargetWithReader(path))
  }

  private fun readTargetWithReader(path: Path): TargetIdeInfo {
    val builder = TargetIdeInfo.newBuilder()
    val parser = TextFormat.Parser.newBuilder().setAllowUnknownFields(true).build()
    path.reader().use { parser.merge(it, builder) }
    return builder.build()
  }
}
