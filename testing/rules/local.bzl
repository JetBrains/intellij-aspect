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

load("@intellij_config//:config.bzl", "config")
load("//intellij:aspect.bzl", "intellij_info_aspect")
load("//modules:cc_info.bzl", "intellij_cc_info_aspect")
load("//modules:java_common_info.bzl", "intellij_java_common_info_aspect")
load("//modules:java_info.bzl", "intellij_java_info_aspect")
load("//modules:jvm_info.bzl", "intellij_jvm_info_aspect")
load("//modules:kotlin_info.bzl", "intellij_kotlin_info_aspect")
load("//modules:py_info.bzl", "intellij_py_info_aspect")
load("//modules:xcode_info.bzl", "intellij_xcode_info_aspect")

_ASPECTS = [
    intellij_xcode_info_aspect,
    intellij_cc_info_aspect,
    intellij_java_info_aspect,
    intellij_kotlin_info_aspect,
    intellij_jvm_info_aspect,
    intellij_java_common_info_aspect,
    intellij_py_info_aspect,
    intellij_info_aspect,
]

def _serialize_output_groups(targets):
    groups = {}

    for target in targets:
        info = target[OutputGroupInfo]

        for group in dir(info):
            groups[group] = groups.get(group, set()) | set([file.path for file in info[group].to_list()])

    return [struct(name = group, files = list(files)) for group, files in groups.items()]

def _intellij_aspect_build_impl(ctx):
    output_proto = ctx.actions.declare_file("%s.intellij-aspect-fixture" % ctx.label.name)

    builder_arguments = proto.encode_text(struct(
        output_proto = output_proto.path,
        output_groups = _serialize_output_groups(ctx.attr.deps),
        bazel_version = config.bazel_version,
    ))

    ctx.actions.run(
        inputs = [file for dep in ctx.attr.deps for file in dep[OutputGroupInfo]["intellij-info"].to_list()],
        executable = ctx.executable._builder,
        arguments = [builder_arguments],
        outputs = [output_proto],
        mnemonic = "LocalFixtureBuilder",
        progress_message = "Building test fixture for %{label} [local build]",
    )

    return [DefaultInfo(files = depset([output_proto]))]

local_test_fixture = rule(
    implementation = _intellij_aspect_build_impl,
    attrs = {
        "deps": attr.label_list(
            mandatory = True,
            aspects = _ASPECTS,
        ),
        "_builder": attr.label(
            cfg = "exec",
            executable = True,
            default = Label("//testing/rules/fixture:builder_bin"),
        ),
    },
)
