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

load(":common.bzl", "intellij_common")

# DependencyType enum; must match Dependency.DependencyType
_COMPILE_TIME = 0
_RUNTIME = 1
_TOOLCHAIN = 2

def _collect_from_toolchains(ctx, result, toolchain_types):
    """Collects dependencies from the toolchains context."""
    if not toolchain_types:
        return

    # toolchains attribute only available in Bazel 8+
    toolchains = getattr(ctx.rule, "toolchains", None)
    if not toolchains:
        return

    for toolchain_type in toolchain_types:
        if toolchain_type in toolchains:
            result.append(toolchains[toolchain_type][intellij_common.TargetInfo].owner)

def _collect_from_attributes(ctx, result, attributes):
    """Collects dependencies from the rule attributes."""
    if not attributes:
        return

    for name in attributes or []:
        result.extend(intellij_common.attr_as_label_list(ctx, name))

def _collect(ctx, attributes = None, toolchain_types = None):
    """Collects dependencies from multiple attributes and toolchains into one list. Returns a depset[Target]."""
    result = []
    _collect_from_attributes(ctx, result, attributes)
    _collect_from_toolchains(ctx, result, toolchain_types)

    return depset(result)

def _find_toolchains(ctx, *args):
    """Finds the toolchain aspect providers for the specific toolchains type."""

    # toolchains attribute only available in Bazel 8+
    toolchains = getattr(ctx.rule, "toolchains", None)
    if not toolchains:
        return

    return [
        toolchains[type]
        for type in args
        if type in toolchains
    ]

intellij_deps = struct(
    COMPILE_TIME = _COMPILE_TIME,
    RUNTIME = _RUNTIME,
    TOOLCHAIN = _TOOLCHAIN,
    collect = _collect,
    find_toolchains = _find_toolchains,
)
