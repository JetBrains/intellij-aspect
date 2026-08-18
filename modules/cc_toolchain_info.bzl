# Copyright 2025 The Bazel Authors.
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
#
# Derived from: https://github.com/bazelbuild/intellij/blob/5ec21e640ed59b316b58559d8e79cb0858e519bd/aspect/intellij_info_impl.bzl

load("@bazel_tools//tools/build_defs/cc:action_names.bzl", "ACTION_NAMES")
load("@rules_cc//cc:defs.bzl", "cc_common")
load("@rules_cc//cc:find_cc_toolchain.bzl", "CC_TOOLCHAIN_TYPE")
load("//common:common.bzl", "intellij_common")
load("//common:ide_info.bzl", "ide_info")
load("//common:output_groups.bzl", "intellij_output_groups")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

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

def _implementation(target, ctx, attrs):
    if not _aspect_guard(target, ctx):
        return None

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
    c_environment = cc_common.get_environment_variables(
        feature_configuration = feature_configuration,
        action_name = ACTION_NAMES.c_compile,
        variables = c_variables,
    )
    cpp_environment = cc_common.get_environment_variables(
        feature_configuration = feature_configuration,
        action_name = ACTION_NAMES.cpp_compile,
        variables = cpp_variables,
    )

    info = intellij_common.struct(
        built_in_include_directory = [str(it) for it in cc_toolchain.built_in_include_directories],
        c_option = c_options,
        cpp_option = cpp_options,
        c_compiler = c_compiler,
        cpp_compiler = cpp_compiler,
        c_environment = c_environment,
        cpp_environment = cpp_environment,
        target_name = cc_toolchain.target_gnu_system_name,
        compiler_name = cc_toolchain.compiler,
        sysroot = cc_toolchain.sysroot,
    )

    return intellij_module.result(info)

_aspect = intellij_module.aspect(
    provider = intellij_provider.CcToolchainInfo,
    implementation = _implementation,
    field = "c_toolchain_ide_info",
)

module = intellij_module.define(
    file = "cc_toolchain_info",
    aspect = _aspect,
    toolchains = [CC_TOOLCHAIN_TYPE],
    fragments = ["cpp"],
    rulesets = ["@rules_cc"],
)
