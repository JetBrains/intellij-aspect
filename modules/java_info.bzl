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

def _get_jvm_outputs(target, ctx):
    if hasattr(target[JavaInfo], "java_outputs"):
        jars = target[JavaInfo].java_outputs
    elif hasattr(target[JavaInfo], "outputs"):
        jars = target[JavaInfo].outputs.jars
    else:
        return None
    source_jars_entries = [
        entry.source_jars
        for entry in jars
        if (hasattr(entry, "source_jars") and entry.source_jars)
    ]
    return intellij_common.struct(
        binary_jars = [
            artifact_location.from_file(jar.class_jar)
            for jar in jars
            if (hasattr(jar, "class_jar") and jar.class_jar)
        ],
        interface_jars = [
            artifact_location.from_file(jar.compile_jar)
            for jar in jars
            if (hasattr(jar, "compile_jar") and jar.compile_jar)
        ],
        source_jars = [
            artifact_location.from_file(jar)
            for entry in source_jars_entries
            for jar in entry.to_list()
        ] or [
            artifact_location.from_file(jar.source_jar)
            for jar in jars
            if (hasattr(jar, "source_jar") and jar.source_jar)
        ],
        jdeps = [
            artifact_location.from_file(jar.jdeps)
            for jar in jars
            if (hasattr(jar, "jdeps") and jar.jdeps)
        ],
    )

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
                jars = [_get_jvm_outputs(target, ctx)],
            ),
        ),
    ]

intellij_java_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.JavaInfo, intellij_provider.JavaCommonContributorJava],
    requires = [intellij_java_toolchain_info_aspect],
    toolchains_aspects = [str(JAVA_TOOLCHAIN_TYPE)],
)
