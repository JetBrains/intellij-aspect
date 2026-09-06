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

import com.intellij.aspect.tools.measure.ReportProto.Metric
import com.intellij.aspect.tools.measure.ReportProto.Series
import kotlin.math.sqrt

internal fun analyze(baseline: Map<String, List<Double>>, aspect: Map<String, List<Double>>): List<Metric> {
  return METRIC_NAMES.map { name ->
    val baselineSeries = series(baseline[name].orEmpty())
    val aspectSeries = series(aspect[name].orEmpty())

    Metric.newBuilder()
      .setName(name)
      .setBaseline(baselineSeries)
      .setAspect(aspectSeries)
      .setCmp(increase(baselineSeries.avg, aspectSeries.avg))
      .build()
  }
}

private fun series(values: List<Double>): Series {
  return Series.newBuilder()
    .addAllValues(values)
    .setAvg(average(values))
    .setStd(deviation(values))
    .build()
}

private fun average(values: List<Double>): Double {
  if (values.isEmpty()) return 0.0

  return values.sum() / values.size
}

/** The standard deviation of [values]. */
private fun deviation(values: List<Double>): Double {
  if (values.size < 2) return 0.0

  val average = average(values)
  val variance = values.sumOf { (it - average) * (it - average) } / (values.size - 1)

  return sqrt(variance)
}

/** The percentage [value] adds over [baseline]. */
internal fun increase(baseline: Double, value: Double): Double {
  if (baseline == 0.0) return 0.0

  return (value - baseline) / baseline * 100
}
