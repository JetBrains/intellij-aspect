# Copyright 2026 JetBrains s.r.o.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:dependencies.bzl", "intellij_deps")
load(":provider.bzl", "intellij_provider")

TOOLCHAIN_TYPE = "@rules_scala//scala:toolchain_type"

SCALA_COMPILER_NAMES = [
    "scala3-compiler",
    "scala-compiler",
]

SCALA_LIBRARY_NAMES = [
    "scala3-interfaces",
    "scala3-library",
    "scala3-reflect",
    "scala-asm",
    "scala-library",
    "scala-reflect",
    "tasty-core",
    "compiler-interface",
]

TOOLCHAIN_DEPS = [
    "_scalatest",
    "_scalatest_runner",
    "_scalatest_reporter",
]

def contains_substring(strings, name):
    for s in strings:
        if s in name:
            return True
    return False

def find_scalac_classpath(runfiles):
    result = []
    found_scala_compiler_jar = False
    for file in runfiles:
        name = file.basename
        if file.extension == "jar" and contains_substring(SCALA_COMPILER_NAMES, name):
            found_scala_compiler_jar = True
            result.append(file)
        elif file.extension == "jar" and contains_substring(SCALA_LIBRARY_NAMES, name):
            result.append(file)
    return result if found_scala_compiler_jar and len(result) >= 2 else []

def extract_scalatest_classpath_targets(rule_attr):
    def extract_from_attr(attr):
        attr_value = getattr(rule_attr, attr, None)
        if attr_value != None:
            return [str(attr_value.label)]
        return []

    return (
        extract_from_attr("_scalatest") +
        extract_from_attr("_scalatest_runner") +
        extract_from_attr("_scalatest_reporter")
    )

def _source_jars(output):
    if hasattr(output, "source_jars"):
        source_jars = output.source_jars
        if type(source_jars) == "depset":
            return source_jars.to_list()
        else:
            # assuming it returns sequence type here
            return source_jars
    if hasattr(output, "source_jar") and output.source_jar != None:
        return [output.source_jar]
    return []

def _get_jvm_outputs(java_outputs):
    return [
        intellij_common.struct(
            binary_jars = [artifact_location.from_file(output.class_jar)] if output.class_jar else [],
            interface_jars = [artifact_location.from_file(output.compile_jar)] if output.compile_jar else [],
            source_jars = [artifact_location.from_file(f) for f in _source_jars(output)],
        )
        for output in java_outputs
    ]

def _get_generated_jars(java_outputs):
    return [
        struct(
            binary_jars = [artifact_location.from_file(output.generated_class_jar)],
            source_jars = [artifact_location.from_file(output.generated_source_jar)],
        )
        for output in java_outputs
        if (output != None) and (output.generated_class_jar != None)
    ]

def _get_outputs(target, ctx, java_outputs, extra_sync):
    resolve_files = []
    resolve_transitives = []
    for out in java_outputs:
        if getattr(out, "compile_jar", None):
            resolve_files += [out.compile_jar]
        elif getattr(out, "ijar", None):
            resolve_files += [out.ijar]
        if getattr(out, "class_jar", None):
            resolve_files += [out.class_jar]
        if getattr(out, "source_jars", None):
            if type(out.source_jars) == "depset":
                resolve_transitives += [out.source_jars]
            else:
                resolve_files += out.source_jars
    if intellij_common.label_is_external(target.label):
        return {intellij_provider.SYNC_OUTPUT: depset(resolve_files + extra_sync, transitive = resolve_transitives)}
    else:
        return {
            intellij_provider.SYNC_OUTPUT: depset(extra_sync),
            intellij_provider.BUILD_OUTPUT: depset(resolve_files, transitive = resolve_transitives),
        }

def _aspect_impl(target, ctx):
    if not ctx.rule.kind.startswith("scala_") and not ctx.rule.kind.startswith("thrift_"):
        return [intellij_provider.ScalaInfo(present = False)]

    compiler_classpath_info = None
    extra_sync = []
    if hasattr(ctx.rule.attr, "_scala_toolchain"):
        common_scalac_opts = ctx.toolchains[TOOLCHAIN_TYPE].scalacopts
        if hasattr(ctx.rule.attr, "_scalac"):
            scalac = ctx.rule.attr._scalac
            compiler_classpath = find_scalac_classpath(scalac.default_runfiles.files.to_list())
            if compiler_classpath:
                compiler_classpath_info = [artifact_location.from_file(f) for f in compiler_classpath]
                if intellij_common.label_is_external(scalac.label):
                    extra_sync = compiler_classpath
    else:
        common_scalac_opts = []

    java_outputs = []
    if hasattr(target, "scala"):
        if hasattr(target.scala, "java_outputs") and provider.java_outputs:
            java_outputs = target.scala.java_outputs
        elif hasattr(target.scala, "outputs") and provider.outputs:
            java_outputs = provider.outputs.jars

    return [intellij_provider.create(
        ctx = ctx,
        provider = intellij_provider.ScalaInfo,
        outputs = _get_outputs(target, ctx, java_outputs, extra_sync),
        dependencies = {
            intellij_deps.TOOLCHAIN: intellij_deps.collect(
                ctx,
                attributes = TOOLCHAIN_DEPS,
            ),
        },
        value = intellij_common.struct(
            scalac_opts = common_scalac_opts + getattr(ctx.rule.attr, "scalacopts", []),
            compiler_classpath = compiler_classpath_info,
            scalatest_classpath_targets = extract_scalatest_classpath_targets(ctx.rule.attr),
        ),
        internal_value = intellij_common.struct(
            java_common = intellij_common.struct(
                jars = _get_jvm_outputs(java_outputs),
                generated_jars = _get_generated_jars(java_outputs),
            ),
        ),
    )]

intellij_scala_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.ScalaInfo],
    toolchains = [
        config_common.toolchain_type(TOOLCHAIN_TYPE, mandatory = False),
    ],
)
