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

package com.intellij.aspect.tools.proto2json

import com.google.devtools.intellij.aspect.Aspect
import com.google.devtools.intellij.aspect.Common
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo
import com.google.protobuf.Descriptors.Descriptor
import com.google.protobuf.DynamicMessage
import com.google.protobuf.InvalidProtocolBufferException
import com.google.protobuf.Message
import com.google.protobuf.TextFormat
import com.google.protobuf.util.JsonFormat
import com.intellij.aspect.testing.rules.fixture.FixtureProto
import com.intellij.aspect.tools.lib.PathArgType
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.charset.CharacterCodingException
import java.nio.charset.CodingErrorAction
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import kotlin.system.exitProcess

/** All messages that can be decoded, keyed by their simple name. */
private val MESSAGES: Map<String, Descriptor> = listOf(
  IntellijIdeInfo.getDescriptor(),
  Common.getDescriptor(),
  Aspect.getDescriptor(),
  FixtureProto.getDescriptor(),
).flatMap { it.messageTypes }.flatMap(::withNested).associateBy { it.name }

private fun withNested(descriptor: Descriptor): List<Descriptor> {
  return listOf(descriptor) + descriptor.nestedTypes.filter { !it.options.mapEntry }.flatMap(::withNested)
}

/** File extension of the fixtures written by the `test_fixture` rule. */
private const val FIXTURE_EXTENSION = ".intellij-aspect-fixture"

enum class Format { AUTO, TEXT, BINARY }

/** Converts a textproto or binary proto into JSON, e.g. to inspect the aspect output with jq. */
fun main(args: Array<String>) {
  val parser = ArgParser("proto2json")

  val input by parser.option(
    PathArgType,
    shortName = "i",
    fullName = "input",
    description = "File to read the proto from, stdin if absent",
  )

  val output by parser.option(
    PathArgType,
    shortName = "o",
    fullName = "output",
    description = "File to write the JSON to, stdout if absent",
  )

  val type by parser.option(
    ArgType.Choice(MESSAGES.keys.sorted(), { it }),
    shortName = "t",
    fullName = "type",
    description = "The message type of the proto, defaults to TestFixture for *$FIXTURE_EXTENSION files and " +
      "TargetIdeInfo otherwise",
  )

  val format by parser.option(
    ArgType.Choice<Format>(),
    shortName = "f",
    fullName = "format",
    description = "The encoding of the proto, auto tries text first and falls back to binary",
  ).default(Format.AUTO)

  val verbose by parser.option(
    ArgType.Boolean,
    shortName = "v",
    fullName = "verbose",
    description = "Show stack traces",
  ).default(false)

  parser.parse(args)

  try {
    val bytes = input?.let { Files.readAllBytes(resolve(it)) } ?: System.`in`.readAllBytes()
    val message = parse(bytes, type?.let(MESSAGES::getValue) ?: defaultType(input), format)
    val json = JsonFormat.printer().preservingProtoFieldNames().print(message) + "\n"

    when (val output = output) {
      null -> print(json)
      else -> Files.writeString(resolve(output), json, StandardCharsets.UTF_8)
    }
  } catch (e: Exception) {
    System.err.println("Error: ${e.message}")
    if (verbose) {
      e.printStackTrace()
    }

    exitProcess(2)
  }
}

/** Guesses the message type from the input file name, if there is one. */
private fun defaultType(input: Path?): Descriptor {
  return when {
    input?.fileName?.toString()?.endsWith(FIXTURE_EXTENSION) == true -> FixtureProto.TestFixture.getDescriptor()
    else -> IntellijIdeInfo.TargetIdeInfo.getDescriptor()
  }
}

/** Resolves relative paths against the directory `bazel run` was invoked from. */
private fun resolve(path: Path): Path {
  val workingDirectory = System.getenv("BUILD_WORKING_DIRECTORY") ?: return path
  return Path.of(workingDirectory).resolve(path)
}

@Throws(IOException::class)
private fun parse(bytes: ByteArray, descriptor: Descriptor, format: Format): Message {
  return when (format) {
    Format.TEXT -> parseText(bytes, descriptor)

    Format.BINARY -> parseBinary(bytes, descriptor)

    Format.AUTO -> try {
      parseText(bytes, descriptor)
    } catch (textError: IOException) {
      try {
        parseBinary(bytes, descriptor)
      } catch (binaryError: IOException) {
        binaryError.addSuppressed(textError)
        throw IOException(
          "input is neither a text nor a binary ${descriptor.name}\n" +
            "  text: ${textError.message}\n" +
            "  binary: ${binaryError.message}",
          binaryError,
        )
      }
    }
  }
}

@Throws(IOException::class)
private fun parseText(bytes: ByteArray, descriptor: Descriptor): Message {
  val text = try {
    StandardCharsets.UTF_8.newDecoder()
      .onMalformedInput(CodingErrorAction.REPORT)
      .onUnmappableCharacter(CodingErrorAction.REPORT)
      .decode(ByteBuffer.wrap(bytes))
      .toString()
  } catch (e: CharacterCodingException) {
    throw IOException("input is not valid UTF-8", e)
  }

  val builder = DynamicMessage.newBuilder(descriptor)
  TextFormat.Parser.newBuilder().build().merge(text, builder)

  return builder.build()
}

@Throws(InvalidProtocolBufferException::class)
private fun parseBinary(bytes: ByteArray, descriptor: Descriptor): Message {
  return DynamicMessage.parseFrom(descriptor, bytes)
}
