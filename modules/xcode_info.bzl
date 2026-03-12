# Copyright 2025 JetBrains s.r.o.
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

load("@rules_cc//cc:defs.bzl", "CcToolchainConfigInfo", "cc_common")
load("@rules_cc//cc:find_cc_toolchain.bzl", "CC_TOOLCHAIN_TYPE")
load("//common:common.bzl", "intellij_common")
load("//common:ide_info.bzl", "ide_info")
load(":provider.bzl", "intellij_provider")

def _find_provider(ctx):
    """
    Tries to find the previously populated XCodeToolchainInfo provider either
    in the rule's attributes. There is no need to check toolchains since there
    is no need to propagate along these edges.
    """

    # check if there is any single target attribute that has the XcodeToolchainInfo
    # provider, this is a little optimization since all attributes where this needs
    # to propagate are single target attributes
    for name in dir(ctx.rule.attr):
        target = intellij_common.attr_as_target(ctx, name)
        if not target:
            continue

        provider = intellij_provider.get(target, intellij_provider.XcodeToolchainInfo)
        if not provider:
            continue

        return provider

    return None

def _has_xcode_version_config(target):
    """Returns True if target has XcodeVersionConfig provider (Bazel 9+)."""
    return apple_common.XcodeVersionConfig != None and apple_common.XcodeVersionConfig in target

def _has_xcode_properties(target):
    """Returns True if target has XcodeProperties provider (Bazel 8)."""
    return apple_common.XcodeProperties != None and apple_common.XcodeProperties in target

def _create_provider(target, ctx):
    """Populates the provider with data from the Xcode configuration.

    Supports both XcodeVersionConfig (Bazel 9+) and XcodeProperties (Bazel 8).
    Prefers XcodeVersionConfig when both are available.
    """

    # prefer XcodeVersionConfig (Bazel 9+) over XcodeProperties (Bazel 8)
    if _has_xcode_version_config(target):
        provider = target[apple_common.XcodeVersionConfig]
        info = intellij_common.struct(
            xcode_version = str(provider.xcode_version()),
            macos_sdk_version = str(provider.sdk_version_for_platform(apple_common.platform.macos)),
        )
    elif _has_xcode_properties(target):
        provider = target[apple_common.XcodeProperties]
        info = intellij_common.struct(
            xcode_version = provider.xcode_version,
            macos_sdk_version = provider.default_macos_sdk_version,
        )
    else:
        return None

    return intellij_provider.create_toolchain(
        provider = intellij_provider.XcodeToolchainInfo,
        info_file = ide_info.write_toolchain(target, ctx, "xcode_ide_info", info),
        owner = target,
    )

def _aspect_impl(target, ctx):
    """Collects Xcode configuration data and propagates it through the toolchain.

    This aspect collects data from either XcodeVersionConfig (Bazel 9+) or
    XcodeProperties (Bazel 8) providers and propagates the data up to the
    top-most toolchain target for access from the cc_info aspect.

    Assumes that the target defining the Xcode configuration is a direct
    dependency of the toolchain configuration.
    """

    # try to create the provider if any of the xcode providers is present
    provider = _create_provider(target, ctx)
    if provider:
        return [provider]

    # propaget the the created provider if this is a toolchain target
    if cc_common.CcToolchainInfo in target or CcToolchainConfigInfo in target:
        provider = _find_provider(ctx)
        if provider:
            return [provider]

    # otherwise default to the empty provider
    return [intellij_provider.XcodeToolchainInfo(present = False)]

intellij_xcode_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.XcodeToolchainInfo],
    toolchains_aspects = [str(CC_TOOLCHAIN_TYPE)],
)
