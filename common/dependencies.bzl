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
load(":provider.bzl", "intellij_provider")

# DependencyType enum; must match Dependency.DependencyType
_COMPILE_TIME = 0
_EXPORTED_COPILE_TIME = 3
_RUNTIME = 1
_TOOLCHAIN = 2

def _collect(ctx, attributes):
    """Collects dependencies from multiple attributes into one list. Returns a depset[IntelliJInfo]|None."""
    result = []

    for name in attributes:
        result.extend(intellij_common.attr_as_info_list(ctx, name))

    return intellij_common.depset(result)

def _collect_toolchains(ctx, toolchain_types):
    """Collects dependencies from multiple toolchains into one list. Returns a depset[IntelliJInfo]|None."""

    # toolchains attribute only available in Bazel 8+
    toolchains = getattr(ctx.rule, "toolchains", None)
    if not toolchains:
        return

    return intellij_common.depset([
        toolchains[toolchain_type.label][intellij_provider.IntelliJInfo]
        for toolchain_type in toolchain_types 
        if toolchain_type.label in toolchains
    ])

intellij_deps = struct(
    COMPILE_TIME = _COMPILE_TIME,
    EXPORTED_COMPILE_TIME = _EXPORTED_COPILE_TIME,
    RUNTIME = _RUNTIME,
    TOOLCHAIN = _TOOLCHAIN,
    collect = _collect,
    collect_toolchains = _collect_toolchains,
)
