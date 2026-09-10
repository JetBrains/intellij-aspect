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

// 1MB is enough to explain a failure, while staying below the limit bazel applies to the output
private const val BUFFER_SIZE = 1 shl 20

private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

open class Logger(sink: OutputStream = System.err, private val name: String? = null) {

  companion object {

    fun quiet(sink: OutputStream = System.err, name: String? = null, capacity: Int = BUFFER_SIZE): Logger {
      return QuietLogger(sink, RingOutputStream(capacity), name)
    }
  }

  private val out = PrintStream(sink)

  @Synchronized
  fun log(message: String) {
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
  fun child(name: String? = null, out: OutputStream? = null): Logger {
    return Logger(name = name ?: this.name, sink = out?.let { tee(it, this.out) } ?: this.out)
  }

  /** Reports an exception. Forces the logger to write to the underlying stream even if it is quiet. */
  open fun error(cause: Throwable) {
    log("ERROR: ${cause.message}")
    cause.stackTraceToString().lines().forEach(::log)
  }
}

private class QuietLogger(
  private val sink: OutputStream,
  private val buffer: RingOutputStream,
  name: String?,
) : Logger(buffer, name) {

  override fun error(cause: Throwable) {
    super.error(cause)
    buffer.transferTo(sink)
  }
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

private class RingOutputStream(private val capacity: Int) : OutputStream() {

  private val buffer = ByteArray(capacity)

  private var offset = 0
  private var wrapped = false

  @Synchronized
  override fun write(b: Int) {
    if (offset == capacity) wrap()
    buffer[offset++] = b.toByte()
  }

  private fun wrap() {
    offset = 0
    wrapped = true
  }

  @Synchronized
  fun transferTo(dst: OutputStream) {
    if (wrapped) dst.write(buffer, offset, capacity - offset)
    dst.write(buffer, 0, offset)
  }
}
