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

import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

class Logger(
  sink: OutputStream = System.err,
  private val quiet: Boolean = false,
  private val name: String? = null,
) {

  private val out = PrintStream(sink)

  @Synchronized
  fun log(message: String) {
    if (quiet) return

    val builder = StringBuilder()

    val time = LocalTime.now().format(TIME_FORMATTER)
    builder.append("[$time] ")

    if (name != null) builder.append("$name: ")

    builder.append(message)
    out.println(builder.toString())

    out.flush()
  }

  /** A stream that logs every line written to it, e.g. the output of a subprocess. */
  fun stream(name: String? = null): OutputStream = LineOutputStream {
    if (name != null) log("$name: $it") else log(it)
  }

  /** Creates a child logger with a new name. */
  fun child(name: String): Logger = Logger(name = name, sink = out, quiet = quiet)
}

private class LineOutputStream(private val sink: (String) -> Unit) : OutputStream() {

  private val buffer = ByteArrayOutputStream()

  @Synchronized
  override fun write(b: Int) {
    if (b == '\n'.code) emit() else buffer.write(b)
  }

  @Synchronized
  override fun flush() {
    if (buffer.size() > 0) emit()
  }

  @Synchronized
  override fun close() {
    flush()
  }

  private fun emit() {
    val line = buffer.toString(Charsets.UTF_8).removeSuffix("\r")
    buffer.reset()

    if (line.isNotBlank()) sink(line)
  }
}
