# Copyright 2026 JetBrains s.r.o. and contributors.
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

load("//testing/rules:defs.bzl", "test_matrix", "test_matrix_suite")

_ASPECTS = [
    "modules:xcode_info.bzl%intellij_xcode_info_aspect",
    "modules:cc_info.bzl%intellij_cc_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

_MODULES = {
    "rules_cc": [
        "0.1.1",
        "0.2.9",
        "0.2.14",
    ],
}

def test_matrix_cc(name, bazel, builtin = False):
    test_matrix(
        name = name + "_bcr",
        aspects = _ASPECTS,
        bazel = bazel,
        modules = _MODULES,
        aspect_deployment = "bcr",
        visibility = ["//visibility:private"],
    )

    test_matrix(
        name = name + "_materialized",
        aspects = _ASPECTS,
        bazel = bazel,
        modules = _MODULES,
        aspect_deployment = "materialized",
        visibility = ["//visibility:private"],
    )

    deps = [name + "_bcr", name + "_materialized"]

    if builtin:
        test_matrix(
            name = name + "_builtin",
            aspects = _ASPECTS,
            bazel = bazel,
            modules = _MODULES,
            aspect_deployment = "builtin",
            visibility = ["//visibility:private"],
        )
        deps.append(name + "_builtin")

    test_matrix_suite(
        name = name,
        deps = deps,
        visibility = ["//visibility:private"],
    )
