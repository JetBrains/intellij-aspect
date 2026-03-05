load("@rules_cc//cc:defs.bzl", "CcInfo", "cc_common")
load("@rules_cc//cc:find_cc_toolchain.bzl", "CC_TOOLCHAIN_TYPE")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:dependencies.bzl", "intellij_deps")
load("//common:make_variables.bzl", "expand_make_variables")
load(":cc_toolchain_info.bzl", "intellij_cc_toolchain_info_aspect")
load(":provider.bzl", "intellij_provider")

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
        sources = artifact_location.from_attr(ctx, "srcs"),
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

def _aspect_impl(target, ctx):
    if not _aspect_guard(target, ctx):
        return [intellij_provider.CcInfo(present = False)]

    # TODO(brendandouglas): target to cpp files only
    compile_files = target[OutputGroupInfo].compilation_outputs if hasattr(target[OutputGroupInfo], "compilation_outputs") else depset([])
    resolve_files = target[CcInfo].compilation_context.headers

    return [intellij_provider.create(
        provider = intellij_provider.CcInfo,
        outputs = {
            "intellij-compile-cpp": compile_files,
            "intellij-resolve-cpp": resolve_files,
        },
        value = intellij_common.struct(
            rule_context = _collect_rule_context(ctx),
            compilation_context = _collect_compilation_context(ctx, target),
        ),
        dependencies = {
            intellij_deps.COMPILE_TIME: intellij_deps.collect(
                ctx,
                attributes = COMPILE_TIME_DEPS,
                toolchain_types = [CC_TOOLCHAIN_TYPE],
            ),
        },
        toolchains = intellij_deps.find_toolchains(ctx, CC_TOOLCHAIN_TYPE),
    )]

intellij_cc_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.CcInfo],
    requires = [intellij_cc_toolchain_info_aspect],
    required_aspect_providers = [[intellij_provider.XcodeToolchainInfo]],
    toolchains_aspects = [str(CC_TOOLCHAIN_TYPE)],
)
