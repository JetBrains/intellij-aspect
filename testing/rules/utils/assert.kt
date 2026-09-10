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
package com.intellij.aspect.testing.rules.utils

import com.google.common.truth.Correspondence
import com.google.common.truth.FailureMetadata
import com.google.common.truth.IterableSubject
import com.google.common.truth.Subject
import com.google.common.truth.Truth.assertAbout
import com.google.devtools.intellij.aspect.Common.ArtifactLocation
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.Dependency
import com.google.devtools.intellij.ideinfo.IntellijIdeInfo.Dependency.DependencyType
import java.nio.file.FileSystems
import java.nio.file.Path

inline fun <reified T : Any> assertNotNull(value: T?): T {
  return value ?: throw AssertionError("value of type ${T::class} is null")
}

class ArtifactLocationsSubject(
  metadata: FailureMetadata,
  private val actual: Iterable<ArtifactLocation>?,
) : IterableSubject(metadata, actual) {

  fun relativePaths(): UsingCorrespondence<ArtifactLocation, String> {
    return comparingElementsUsing(Correspondence.transforming({ it?.relativePath }, "has relative path"))
  }
}

private val ARTIFACT_LOCATIONS_SUBJECT_FACTORY = Subject.Factory(::ArtifactLocationsSubject)

fun assertThatArtifacts(actual: Iterable<ArtifactLocation>?): ArtifactLocationsSubject {
  return assertAbout(ARTIFACT_LOCATIONS_SUBJECT_FACTORY).that(actual)
}

class DependenciesSubject(
  metadata: FailureMetadata,
  private val actual: Iterable<Dependency>?,
) : IterableSubject(metadata, actual) {

  fun labels(): UsingCorrespondence<Dependency, String> {
    return comparingElementsUsing(Correspondence.transforming({ it?.target?.label }, "has target label"))
  }

  fun keys(): UsingCorrespondence<Dependency, IntellijIdeInfo.TargetKey> {
    return comparingElementsUsing(Correspondence.transforming({ it?.target }, "has target key"))
  }

  fun withType(type: DependencyType): DependenciesSubject {
    return check("dependencies of type %s", type)
      .about(DEPENDENCIES_SUBJECT_FACTORY)
      .that(actual?.filter { it.dependencyType == type })
  }
}

private val DEPENDENCIES_SUBJECT_FACTORY = Subject.Factory(::DependenciesSubject)

fun assertThatDeps(actual: Iterable<Dependency>?): DependenciesSubject {
  return assertAbout(DEPENDENCIES_SUBJECT_FACTORY).that(actual)
}

class OutputGroupSubject(
  metadata: FailureMetadata,
  private val actual: Iterable<String>?,
) : IterableSubject(metadata, actual) {

  fun containsFile(suffix: String) {
    check("contains file with suffix %s", suffix)
      .that(actual?.filter { Path.of(it).endsWith(suffix) })
      .isNotEmpty()
  }

  fun doesNotContainFile(suffix: String) {
    check("contains no file with suffix %s", suffix)
      .that(actual?.filter { Path.of(it).endsWith(suffix) })
      .isEmpty()
  }

  /** Asserts that the group contains a file matching [glob], e.g. `materialized_*main.jdeps`. */
  fun containsFileMatching(glob: String) {
    val matcher = FileSystems.getDefault().getPathMatcher("glob:**$glob")

    check("contains file matching %s", glob)
      .that(actual?.filter { matcher.matches(Path.of(it)) })
      .isNotEmpty()
  }
}

private val OUTPUT_GROUP_SUBJECT_FACTORY = Subject.Factory(::OutputGroupSubject)

fun assertThatOutputGroup(actual: Iterable<String>?): OutputGroupSubject {
  return assertAbout(OUTPUT_GROUP_SUBJECT_FACTORY).that(actual)
}
