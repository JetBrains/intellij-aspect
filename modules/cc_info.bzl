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

load("@rules_cc//cc:defs.bzl", "CcInfo", "cc_common")
load("@rules_cc//cc:find_cc_toolchain.bzl", "CC_TOOLCHAIN_TYPE")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:dependencies.bzl", "intellij_deps")
load("//common:make_variables.bzl", "expand_make_variables")
load("//common:provider.bzl", "intellij_provider")
load("//common:output_groups.bzl", "intellij_output_groups")
load(":module.bzl", "intellij_module")

# additional compile time dependencies collected for cc targets
COMPILE_TIME_DEPS = [
    "_stl",
    "_cc_toolchain",
    "implementation_deps",  # for cc_library
    "malloc",  # for cc_binary
]

def _collect_rule_context(ctx):
    """Collect additional information from the rule attributes of cc_xxx rules."""
    if not ctx.rule.kind.startswith("cc_"):
        return struct()

    return intellij_common.struct(
        headers = artifact_location.from_attr(ctx, "hdrs"),
        textual_headers = artifact_location.from_attr(ctx, "textual_hdrs"),
        copts = expand_make_variables(ctx, True, intellij_common.attr_as_list(ctx, "copts")),
        conlyopts = expand_make_variables(ctx, True, intellij_common.attr_as_list(ctx, "conlyopts")),
        cxxopts = expand_make_variables(ctx, True, intellij_common.attr_as_list(ctx, "cxxopts")),
        args = expand_make_variables(ctx, True, intellij_common.attr_as_list(ctx, "args")),
        include_prefix = intellij_common.attr_as_str(ctx, "include_prefix"),
        strip_include_prefix = intellij_common.attr_as_str(ctx, "strip_include_prefix"),
    )

def _collect_compilation_context(ctx, target):
    """Collect information from the compilation context provided by the CcInfo provider."""
    compilation_context = target[CcInfo].compilation_context

    # collect non-propagated attributes before potentially merging with implementation deps
    local_defines = compilation_context.local_defines.to_list()

    # merge current compilation context with context of implementation dependencies
    if ctx.rule.kind.startswith("cc_") and hasattr(ctx.rule.attr, "implementation_deps"):
        impl_deps = ctx.rule.attr.implementation_deps

        compilation_context = cc_common.merge_compilation_contexts(
            compilation_contexts = [compilation_context] + [it[CcInfo].compilation_context for it in impl_deps],
        )

    # external_includes available since bazel 7
    external_includes = getattr(compilation_context, "external_includes", depset()).to_list()

    return intellij_common.struct(
        headers = [artifact_location.from_file(it) for it in compilation_context.headers.to_list()],
        defines = compilation_context.defines.to_list() + local_defines,
        includes = compilation_context.includes.to_list(),
        quote_includes = compilation_context.quote_includes.to_list(),
        # both system and external includes are added using `-isystem`
        system_includes = compilation_context.system_includes.to_list() + external_includes,
    )

def _aspect_guard(target, ctx):
    """Returns true if the aspect should be applied to the current target."""
    if CcInfo not in target:
        return False

    # ignore cc_proto_library, attach to proto_library with aspect attached instead
    if ctx.rule.kind == "cc_proto_library":
        return False

    # go targets always provide CcInfo, usually it's empty and even if it isn't we don't handle it
    if ctx.rule.kind.startswith("go_"):
        return False

    # targets built under exec configuration are most likely used as local tool
    if intellij_common.is_exec_configuration(ctx):
        return False

    return True

def _implementation(target, ctx, attr):
    if not _aspect_guard(target, ctx):
        return None

    resolve_files = target[CcInfo].compilation_context.headers

    return intellij_module.result(
        outputs = {
            intellij_output_groups.BUILD: resolve_files,
            intellij_output_groups.SYNC: resolve_files,
        },
        value = intellij_common.struct(
            rule_context = _collect_rule_context(ctx),
            compilation_context = _collect_compilation_context(ctx, target),
        ),
        dependencies = {
            intellij_deps.COMPILE_TIME: intellij_deps.collect(ctx, COMPILE_TIME_DEPS),
        },
    )

_aspect = intellij_module.aspect(
    provider = intellij_provider.CcInfo,
    implementation = _implementation,
    field = "c_ide_info",
)

module = intellij_module.define(
    file = "cc_info",
    aspect = _aspect,
    toolchains = [CC_TOOLCHAIN_TYPE],
    aspect_providers = [CcInfo],
    rulesets = ["@rules_cc"],
)
