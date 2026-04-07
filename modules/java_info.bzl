# Copyright 2026 JetBrains s.r.o.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

load("@rules_java//java:defs.bzl", "JavaInfo")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:dependencies.bzl", "intellij_deps")
load("//common:make_variables.bzl", "expand_make_variables")
load(":java_toolchain_info.bzl", "JAVA_TOOLCHAIN_TYPE", "intellij_java_toolchain_info_aspect")
load(":provider.bzl", "intellij_provider")

COMPILE_TIME_DEPS = [
    "jars",
    "_java_toolchain",
    "_jvm",
    "runtime_jdk",
]

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

def _get_jvm_outputs(target, ctx):
    return [
        intellij_common.struct(
            binary_jars = [artifact_location.from_file(output.class_jar)] if output.class_jar else [],
            interface_jars = [artifact_location.from_file(output.compile_jar)] if output.compile_jar else [],
            source_jars = [artifact_location.from_file(f) for f in _source_jars(output)],
        )
        for output in target[JavaInfo].java_outputs
    ]

def _has_api_generating_plugins(target, ctx):
    return len(target[JavaInfo].api_generating_plugins.processor_classes.to_list()) > 0

def _aspect_impl(target, ctx):
    if not JavaInfo in target:
        return [
            intellij_provider.JavaInfo(present = False),
            intellij_provider.JavaCommonContributorJava(present = False),
        ]
    return [
        intellij_provider.create(
            provider = intellij_provider.JavaInfo,
            value = intellij_common.struct(
                full_compile_jars = artifact_location.from_depset(target[JavaInfo].full_compile_jars),
                has_api_generating_plugins = _has_api_generating_plugins(target, ctx),
            ),
            dependencies = {
                intellij_deps.COMPILE_TIME: intellij_deps.collect(
                    ctx,
                    attributes = COMPILE_TIME_DEPS,
                    toolchain_types = [JAVA_TOOLCHAIN_TYPE],
                ),
            },
            toolchains = intellij_deps.find_toolchains(ctx, JAVA_TOOLCHAIN_TYPE),
        ),
        intellij_provider.create(
            provider = intellij_provider.JavaCommonContributorJava,
            value = intellij_common.struct(
                jars = _get_jvm_outputs(target, ctx),
                jdeps = [artifact_location.from_file(jo.jdeps) for jo in target[JavaInfo].java_outputs if jo.jdeps != None],
            ),
        ),
    ]

intellij_java_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.JavaInfo, intellij_provider.JavaCommonContributorJava],
    requires = [intellij_java_toolchain_info_aspect],
    toolchains_aspects = [str(JAVA_TOOLCHAIN_TYPE)],
)
