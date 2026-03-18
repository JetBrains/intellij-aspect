package com.intellij.aspect.private.lib.utils

import java.io.OutputStream

private class TeeOutputStream(private val streams: List<OutputStream>) : OutputStream() {

  override fun write(b: Int) {
    streams.forEach { it.write(b) }
  }

  override fun flush() {
    streams.forEach { it.flush() }
  }

  override fun close() {
    streams.forEach { it.close() }
  }
}

fun tee(vararg streams: OutputStream): OutputStream {
  return TeeOutputStream(streams.toList())
}