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

package com.intellij.aspect.tools.measure

import com.google.gson.GsonBuilder
import com.google.protobuf.TextFormat
import com.intellij.aspect.tools.measure.ReportProto.Report
import java.nio.file.Files
import java.nio.file.Path
import kotlin.system.exitProcess

// hardcoded list of metrics to extract for a benchmark report
private val BENCHMARK_METRICS = listOf(
  "used-heap-size-after-gc",
  "peak-heap-size",
  "analysis_heap_used",
  "analysis_heap_committed",
)

private data class BenchmarkResult(
  val name: String,
  val unit: String,
  val value: Double,
)

fun main(args: Array<String>) {
  if (args.isEmpty()) {
    System.err.println("Usage: benchmark <report.textproto>...")
    exitProcess(2)
  }

  val results = args.flatMap { argument ->
    val path = Path.of(argument)
    val benchmark = path.fileName.toString().removeSuffix(".textproto")

    val report = Report.newBuilder().also { builder ->
      Files.newBufferedReader(path).use { reader ->
        TextFormat.getParser().merge(reader, builder)
      }
    }.build()

    val metrics = report.metricsList.associateBy { it.name }
    val missing = BENCHMARK_METRICS.filterNot(metrics::containsKey)
    if (missing.isNotEmpty()) {
      System.err.println("Metrics missing from $path: ${missing.sorted().joinToString()}")
      exitProcess(2)
    }

    BENCHMARK_METRICS.map { name ->
      BenchmarkResult("$benchmark:$name", "%", metrics.getValue(name).cmp)
    }
  }

  println(GsonBuilder().setPrettyPrinting().create().toJson(results))
}
