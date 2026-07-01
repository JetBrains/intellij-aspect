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

load("//modules:cc_info.bzl", "intellij_cc_info_aspect")
load("//modules:go_info.bzl", "intellij_go_info_aspect")
load("//modules:java_common_info.bzl", "intellij_java_common_info_aspect")
load("//modules:java_info.bzl", "intellij_java_info_aspect")
load("//modules:jvm_info.bzl", "intellij_jvm_info_aspect")
load("//modules:kotlin_info.bzl", "intellij_kotlin_info_aspect")
load("//modules:proto_info.bzl", "intellij_proto_info_aspect")
load("//modules:protobuf_info.bzl", "intellij_protobuf_info_aspect")
load("//modules:py_info.bzl", "intellij_py_info_aspect")
load("//modules:python_info.bzl", "intellij_python_info_aspect")
load("//modules:scala_info.bzl", "intellij_scala_info_aspect")
load("//modules:xcode_info.bzl", "intellij_xcode_info_aspect")
load("//common:platform.bzl", "platform")
load(":aspect.bzl", "intellij_info_aspect")

# Aspects are grouped per language so that a target only runs the aspects for
# its own language. This matters because some aspects (e.g. go, scala) force
# resolution of their toolchain; keeping them off unrelated deps means a consumer
# only needs the rule sets and toolchains for the languages they test.
_LANGUAGE_ASPECTS = {
    "cc": [
        intellij_xcode_info_aspect,
        intellij_cc_info_aspect,
        intellij_info_aspect,
    ],
    "go": [
        intellij_go_info_aspect,
        intellij_info_aspect,
    ],
    "java": [
        intellij_java_info_aspect,
        intellij_jvm_info_aspect,
        intellij_java_common_info_aspect,
        intellij_info_aspect,
    ],
    "kotlin": [
        intellij_kotlin_info_aspect,
        intellij_jvm_info_aspect,
        intellij_java_common_info_aspect,
        intellij_info_aspect,
    ],
    "proto": [
        intellij_proto_info_aspect,
        intellij_protobuf_info_aspect,
        intellij_info_aspect,
    ],
    "python": [
        intellij_py_info_aspect,
        intellij_python_info_aspect,
        intellij_info_aspect,
    ],
    "scala": [
        intellij_scala_info_aspect,
        intellij_jvm_info_aspect,
        intellij_java_common_info_aspect,
        intellij_info_aspect,
    ],
}

def _intellij_aspect_build_impl(ctx):
    info_files = [
        dep[OutputGroupInfo]["intellij-info"]
        for language in _LANGUAGE_ASPECTS
        for dep in getattr(ctx.attr, language)
    ]

    return [DefaultInfo(files = depset(transitive = info_files))]

_intellij_aspect_build = rule(
    implementation = _intellij_aspect_build_impl,
    attrs = {
        language: attr.label_list(
            aspects = aspects,
            doc = "%s targets to apply the IntelliJ aspect to." % language,
        )
        for language, aspects in _LANGUAGE_ASPECTS.items()
    },
)

# derived from: https://github.com/bazelbuild/bazel-skylib/blob/main/rules/build_test.bzl
def _build_test_impl(ctx):
    extension = ".bat" if platform.is_windows() else ".sh"
    content = "exit 0" if platform.is_windows() else "#!/usr/bin/env bash\nexit 0"

    executable = ctx.actions.declare_file(ctx.label.name + extension)
    ctx.actions.write(output = executable, is_executable = True, content = content)

    return [DefaultInfo(
        files = depset([executable]),
        executable = executable,
        runfiles = ctx.runfiles(files = ctx.files.targets),
    )]

_build_test = rule(
    implementation = _build_test_impl,
    test = True,
    attrs = {"targets": attr.label_list(mandatory = True)},
)

def intellij_aspect_test(
        name,
        cc = [],
        go = [],
        java = [],
        kotlin = [],
        proto = [],
        python = [],
        scala = [],
        **kwargs):
    """Asserts the IntelliJ aspect builds successfully over the given deps.

    Applies the aspect to each language's deps and wraps the result in a build
    test, so `bazel test` passes if and only if the aspect builds cleanly. Only
    the languages you pass are exercised, so you only need the rule sets and
    toolchains for those languages.

    Args:
        name: Name of the test target.
        cc: C/C++ targets to apply the aspect to.
        go: go targets to apply the aspect to.
        java: java targets to apply the aspect to.
        kotlin: kotlin targets to apply the aspect to.
        proto: proto targets to apply the aspect to.
        python: python targets to apply the aspect to.
        scala: scala targets to apply the aspect to.
        **kwargs: Passed through to the underlying test (e.g. tags, visibility, size).
    """
    build = "%s_build" % name

    _intellij_aspect_build(
        name = build,
        cc = cc,
        go = go,
        java = java,
        kotlin = kotlin,
        proto = proto,
        python = python,
        scala = scala,
        testonly = True,
        tags = ["manual"],
    )

    _build_test(
        name = name,
        targets = [build],
        **kwargs
    )
