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
package com.intellij.aspect.testing.tests.lib

import com.google.common.truth.Correspondence
import com.google.common.truth.IterableSubject
import com.google.devtools.intellij.aspect.Common.ArtifactLocation
import com.google.devtools.intellij.ideinfo.IdeInfo.Dependency
import com.google.devtools.intellij.ideinfo.IdeInfo.Dependency.DependencyType

inline fun <reified T : Any> assertNotNull(value: T?): T {
  return value ?: throw AssertionError("value of type ${T::class} is null")
}

fun isMacOS(): Boolean = System.getProperty("os.name").lowercase().contains("mac")

fun isLinux(): Boolean = System.getProperty("os.name").lowercase().contains("linux")

fun isWindows(): Boolean = System.getProperty("os.name").lowercase().contains("windows")

fun IterableSubject.relativeArtifactPath(relativePath: String? = null): IterableSubject.UsingCorrespondence<ArtifactLocation, String> {
  val predicate = Correspondence.BinaryPredicate<ArtifactLocation, String> { location, path ->
    location.relativePath == path && (relativePath == null || location.relativePath == relativePath)
  }

  return comparingElementsUsing(Correspondence.from(predicate, "artifact relative path"))
}

fun IterableSubject.dependencyLabels(type: DependencyType): IterableSubject.UsingCorrespondence<Dependency, String> {
  val predicate = Correspondence.BinaryPredicate<Dependency, String> { dependency, label ->
    dependency.dependencyType == type && dependency.target.label == label
  }

  return comparingElementsUsing(Correspondence.from(predicate, "dependency label"))
}
