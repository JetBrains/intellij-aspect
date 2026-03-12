# Copyright 2025 The Bazel Authors. All rights reserved.
# Modified by JetBrains s.r.o.
# Source: https://github.com/bazelbuild/intellij/blob/master/aspect/intellij_info_impl.bzl
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

load("@rules_python//python:defs.bzl", "PyInfo")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:make_variables.bzl", "expand_make_variables")
load(":provider.bzl", "intellij_provider")

# PythonVersion enum; must match PyIdeInfo.PythonVersion
PY2 = 1
PY3 = 2

# PythonCompatVersion enum; must match PyIdeInfo.PythonSrcsVersion
SRC_PY2 = 1
SRC_PY3 = 2
SRC_PY2AND3 = 3
SRC_PY2ONLY = 4
SRC_PY3ONLY = 5

SRCS_VERSION_MAPPING = {
    "PY2": SRC_PY2,
    "PY3": SRC_PY3,
    "PY2AND3": SRC_PY2AND3,
    "PY2ONLY": SRC_PY2ONLY,
    "PY3ONLY": SRC_PY3ONLY,
}

def _get_srcs_version(ctx):
    srcs_version = getattr(ctx.rule.attr, "srcs_version", "PY2AND3")
    return SRCS_VERSION_MAPPING.get(srcs_version, SRC_PY2AND3)

def _get_py_launcher(ctx):
    """Returns the python launcher for a given rule."""
    if getattr(ctx.rule.attr, "_launcher", None) != None:
        return str(ctx.rule.attr._launcher.label)
    else:
        return None

def _aspect_impl(target, ctx):
    if PyInfo not in target:
        return [intellij_provider.PyInfo(present = False)]

    to_build = target[PyInfo].transitive_sources

    # TODO: port python get_code_generator_rule_names

    return [intellij_provider.create(
        provider = intellij_provider.PyInfo,
        outputs = {
            "intellij-compile-py": to_build,
            "intellij-resolve-py": to_build,
        },
        value = intellij_common.struct(
            launcher = _get_py_launcher(ctx),
            python_version = PY3,
            sources = artifact_location.from_attr(ctx, "srcs"),
            srcs_version = _get_srcs_version(ctx),
            args = expand_make_variables(ctx, False, intellij_common.attr_as_list(ctx, "args")),
            imports = intellij_common.attr_as_list(ctx, "imports"),
        ),
    )]

intellij_py_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    fragments = ["py"],
    provides = [intellij_provider.PyInfo],
)
