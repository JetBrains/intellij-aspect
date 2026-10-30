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

load("//common:common.bzl", "intellij_common")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

# all PLATFORM_* labels are rewritten on deployment, see TransformPlatformsRepository
PLATFORM_LINUX = "@platforms//os:linux"
PLATFORM_MACOS = "@platforms//os:macos"
PLATFORM_WINDOWS = "@platforms//os:windows"

PLATFORM_AMD = "@platforms//cpu:x86_64"
PLATFORM_ARM = "@platforms//cpu:aarch64"

# OS enum; must match PlatformInfo.OS
OS_UNKNOWN = 0
OS_LINUX = 1
OS_MACOS = 2
OS_WINDOWS = 3

OS_MAPPINGS = {
    "_os_linux": OS_LINUX,
    "_os_macos": OS_MACOS,
    "_os_windows": OS_WINDOWS,
}

# ARCH enum; must match PlatformInfo.ARCH
ARCH_UNKNOWN = 0
ARCH_AMD = 1
ARCH_ARM = 2

ARCH_MAPPINGS = {
    "_arch_amd": ARCH_AMD,
    "_arch_arm": ARCH_ARM,
}

def _target_constraint(ctx, attr, mappings, unknown):
    """Returns the value of the first constraint satisfied by the target platform."""
    for key, value in mappings.items():
        if ctx.target_platform_has_constraint(attr[key][platform_common.ConstraintValueInfo]):
            return value

    return unknown

def _implementation(target, ctx, attr):
    # only report the platform for targets reported by any other module
    if not [it for it in attr["_results"].values() if it.value != None]:
        return None

    return intellij_module.result(
        value = intellij_common.struct(
            os = intellij_common.encode_enum(_target_constraint(ctx, attr, OS_MAPPINGS, OS_UNKNOWN)),
            arch = intellij_common.encode_enum(_target_constraint(ctx, attr, ARCH_MAPPINGS, ARCH_UNKNOWN)),
        ),
    )

_aspect = intellij_module.aspect(
    provider = intellij_provider.PlatformInfo,
    implementation = _implementation,
    field = "platform_info",
    attrs = {
        "_os_linux": attr.label(default = Label(PLATFORM_LINUX)),
        "_os_macos": attr.label(default = Label(PLATFORM_MACOS)),
        "_os_windows": attr.label(default = Label(PLATFORM_WINDOWS)),
        "_arch_amd": attr.label(default = Label(PLATFORM_AMD)),
        "_arch_arm": attr.label(default = Label(PLATFORM_ARM)),
    },
)

module = intellij_module.define(
    file = "platform_info",
    aspect = _aspect,
    rulesets = ["PLATFORMS"],
)
