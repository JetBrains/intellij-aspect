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

load("@rules_cc//cc:defs.bzl", "cc_common")
load("@rules_cc//cc:find_cc_toolchain.bzl", "CC_TOOLCHAIN_TYPE")
load("//common:common.bzl", "intellij_common")
load("//common:ide_info.bzl", "ide_info")
load("//common:output_groups.bzl", "intellij_output_groups")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

def _find_result(ctx):
    """
    Tries to find a result previously propagated by any of the rule's
    attributes. There is no need to check toolchains here.
    """

    for name in dir(ctx.rule.attr):
        for target in intellij_common.attr_as_label_list(ctx, name):
            result = intellij_module.lookup_target(target, intellij_provider.XcodeInfo)
            if result:
                return result

    return None

def _has_xcode_version_config(target):
    """Returns True if target has XcodeVersionConfig provider (Bazel 9+)."""
    return apple_common.XcodeVersionConfig != None and apple_common.XcodeVersionConfig in target

def _has_xcode_properties(target):
    """Returns True if target has XcodeProperties provider (Bazel 8)."""
    return apple_common.XcodeProperties != None and apple_common.XcodeProperties in target

def _create_result(target, ctx):
    """Creates the result with data from the Xcode configuration.

    Supports both XcodeVersionConfig (Bazel 9+) and XcodeProperties (Bazel 8).
    Prefers XcodeVersionConfig when both are available.
    """

    # prefer XcodeVersionConfig (Bazel 9+) over XcodeProperties (Bazel 8)
    if _has_xcode_version_config(target):
        provider = target[apple_common.XcodeVersionConfig]
        return intellij_common.struct(
            xcode_version = str(provider.xcode_version()),
            macos_sdk_version = str(provider.sdk_version_for_platform(apple_common.platform.macos)),
        )

    if _has_xcode_properties(target):
        provider = target[apple_common.XcodeProperties]
        return intellij_common.struct(
            xcode_version = provider.xcode_version,
            macos_sdk_version = provider.default_macos_sdk_version,
        )

    return None

def _implementation(target, ctx, attr):
    """Collects Xcode configuration data and propagates it up to the cc toolchain.

    This aspect collects data from either XcodeVersionConfig (Bazel 9+) or
    XcodeProperties (Bazel 8) providers and propagates the data through any
    intermediate target (e.g. toolchain configs or helper rules of rules based
    toolchains) up to the first cc toolchain target, where it is exposed.
    """

    # either create the result from the xcode providers or pick it up from a dependency
    result = _create_result(target, ctx) or _find_result(ctx)
    if not result:
        return None

    # expose the result on the cc toolchain and stop propagating
    if cc_common.CcToolchainInfo in target:
        return intellij_module.result(result)

    # otherwise only propagate the result, without a value no ide info is written
    return intellij_module.result(None, cross_target_internal_value = result)

_aspect = intellij_module.aspect(
    provider = intellij_provider.XcodeInfo,
    implementation = _implementation,
    field = "xcode_ide_info",
)

module = intellij_module.define(
    file = "xcode_info",
    aspect = _aspect,
    toolchains = [CC_TOOLCHAIN_TYPE],
    rulesets = ["CC"],
)
