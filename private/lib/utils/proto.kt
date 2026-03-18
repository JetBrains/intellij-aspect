package com.intellij.aspect.private.lib.utils

import com.google.protobuf.Message
import com.google.protobuf.TextFormat
import java.io.IOException

/** Parses a text-format protobuf string into [T] using reflection. */
@Throws(IOException::class)
inline fun <reified T : Message> parseTextProto(text: String): T {
  try {
    val builder = T::class.java.getMethod("newBuilder").invoke(null) as Message.Builder
    TextFormat.Parser.newBuilder().build().merge(text, builder)
    return builder.build() as T
  } catch (e: Throwable) {
    throw IOException("could not parse text proto ${T::class}", e)
  }
}
