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

load("@bazel_tools//tools/build_defs/cc:action_names.bzl", "ACTION_NAMES")
load("@rules_cc//cc:defs.bzl", "cc_common")
load("@rules_cc//cc:find_cc_toolchain.bzl", "CC_TOOLCHAIN_TYPE")
load("//common:common.bzl", "intellij_common")
load("//common:ide_info.bzl", "ide_info")
load(":provider.bzl", "intellij_provider")

# Defensive list of features that can appear in the C++ toolchain, but which we
# definitely don't want to enable (when enabled, they'd contribute command line
# flags that don't make sense in the context of intellij info).
UNSUPPORTED_FEATURES = [
    "thin_lto",
    "module_maps",
    "use_header_modules",
    "fdo_instrument",
    "fdo_optimize",
]

def _aspect_guard(target, ctx):
    """Returns true if the aspect should be applied to the current target."""
    if not cc_common.CcToolchainInfo in target:
        return False

    # targets built under exec configuration are most likely used as local tool
    if intellij_common.is_exec_configuration(ctx):
        return False

    return True

def _aspect_impl(target, ctx):
    if not _aspect_guard(target, ctx):
        return [intellij_provider.CcToolchainInfo(present = False)]

    cc_toolchain = target[cc_common.CcToolchainInfo]
    cpp_fragment = ctx.fragments.cpp

    copts = cpp_fragment.copts
    cxxopts = cpp_fragment.cxxopts
    conlyopts = cpp_fragment.conlyopts

    feature_configuration = cc_common.configure_features(
        ctx = ctx,
        cc_toolchain = cc_toolchain,
        requested_features = ctx.features,
        unsupported_features = ctx.disabled_features + UNSUPPORTED_FEATURES,
    )
    c_variables = cc_common.create_compile_variables(
        feature_configuration = feature_configuration,
        cc_toolchain = cc_toolchain,
        user_compile_flags = copts + conlyopts,
    )
    cpp_variables = cc_common.create_compile_variables(
        feature_configuration = feature_configuration,
        cc_toolchain = cc_toolchain,
        user_compile_flags = copts + cxxopts,
    )
    c_options = cc_common.get_memory_inefficient_command_line(
        feature_configuration = feature_configuration,
        action_name = ACTION_NAMES.c_compile,
        variables = c_variables,
    )
    cpp_options = cc_common.get_memory_inefficient_command_line(
        feature_configuration = feature_configuration,
        action_name = ACTION_NAMES.cpp_compile,
        variables = cpp_variables,
    )
    c_compiler = cc_common.get_tool_for_action(
        feature_configuration = feature_configuration,
        action_name = ACTION_NAMES.c_compile,
    )
    cpp_compiler = cc_common.get_tool_for_action(
        feature_configuration = feature_configuration,
        action_name = ACTION_NAMES.cpp_compile,
    )

    info = intellij_common.struct(
        built_in_include_directory = [str(it) for it in cc_toolchain.built_in_include_directories],
        c_option = c_options,
        cpp_option = cpp_options,
        c_compiler = c_compiler,
        cpp_compiler = cpp_compiler,
        target_name = cc_toolchain.target_gnu_system_name,
        compiler_name = cc_toolchain.compiler,
        sysroot = cc_toolchain.sysroot,
    )

    return [intellij_provider.create_toolchain(
        provider = intellij_provider.CcToolchainInfo,
        info_file = ide_info.write_toolchain(target, ctx, "c_toolchain_ide_info", info),
        owner = target,
    )]

intellij_cc_toolchain_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    fragments = ["cpp"],
    provides = [intellij_provider.CcToolchainInfo],
    toolchains_aspects = [str(CC_TOOLCHAIN_TYPE)],
)
