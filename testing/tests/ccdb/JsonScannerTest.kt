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
package com.intellij.aspect.testing.tests.ccdb

import com.google.common.truth.Truth.assertThat
import com.intellij.aspect.tools.ccdb.JsonScanner
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.nio.file.Path

@RunWith(JUnit4::class)
class JsonScannerTest {

  @Test
  fun testReadsAllFiles() {
    val files = JsonScanner.scan(Path.of("testing/tests/ccdb/compile_commands.json"), "file")

    assertThat(files).containsExactly(
      "external/abseil-cpp+/absl/time/civil_time.cc",
      "external/abseil-cpp+/absl/time/clock.cc",
      "external/abseil-cpp+/absl/time/duration.cc",
      "external/abseil-cpp+/absl/time/format.cc",
      "external/abseil-cpp+/absl/time/time.cc",
    ).inOrder()
  }
}
