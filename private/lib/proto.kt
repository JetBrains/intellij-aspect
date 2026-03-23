/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
