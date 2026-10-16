load("@rules_cc//cc:defs.bzl", "CcInfo", "cc_common")
load("@rules_cc//cc:find_cc_toolchain.bzl", "CC_TOOLCHAIN_TYPE")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:dependencies.bzl", "intellij_deps")
load("//common:make_variables.bzl", "expand_make_variables")
load("//common:output_groups.bzl", "intellij_output_groups")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

OUTPUT_GROUP = "compilecommands"

EXECROOT_PLACEHOLDER = "{EXECROOT}"

CPP_EXTENSION = ["cpp", "cc"]

def _compilecommands_entry(c_arguments, cpp_arguments, info, file):
    is_cpp = file.extension in CPP_EXTENSION

    rule_ctx = info.rule_context
    comp_ctx = info.compilation_context

    arguments = list(cpp_arguments if is_cpp else c_arguments)
    arguments.extend(rule_ctx.copts)
    arguments.extend(rule_ctx.cxxopts if is_cpp else rule_ctx.conlyopts)

    for define in comp_ctx.defines:
        arguments.append("-D%s" % define)

    for include in comp_ctx.includes:
        arguments.append("-I%s" % include)

    for include in comp_ctx.quote_includes:
        arguments.append("-iquote%s" % include)

    for include in comp_ctx.system_includes:
        arguments.append("-isystem%s" % include)

    return struct(
        director = EXECROOT_PLACEHOLDER,
        arguments = arguments,
        file = file.path,
    )

def _compilecommands(target, ctx, attr):
    """Creates the commpile commands json file and writes it."""
    target_info = intellij_module.lookup_self(attr, intellij_provider.CcInfo)
    if not target_info:
        return

    toolchain_info = intellij_module.lookup_self(attr, intellij_provider.CcToolchainInfo)
    if not toolchain_info:
        return

    c_arguments = [toolchain_info.c_compiler] + toolchain_info.c_option
    cpp_arguments = [toolchain_info.cpp_compiler] + toolchain_info.cpp_option

    for include in toolchain_info.built_in_include_directory:
        c_arguments.append("-isystem%s" % include)
        cpp_arguments.append("-isystem%s" % include)

    if toolchain_info.sysroot:
        c_arguments.append("--sysroot=%s" % toolchain_info.sysroot)
        cpp_arguments.append("--sysroot=%s" % toolchain_info.sysroot)

    commands = [
        _compilecommands_entry(c_arguments, cpp_arguments, target_info, file)
        for target in intellij_common.attr_as_label_list(target, "srcs")
        for file in target.files.to_list()
    ]

    file = ctx.action.declare_file(target.label + "-intellij/compile_commands.json")
    ctx.actions.write(file, json.encode(commands))

    return intellij_module.result(None, outputs = {OUTPUT_GROUP: depset([file])})

def _forward_toolchain(target, ctx, attr):
    """Forward the toolchain info so it can be querried later."""
    toolchain_info = intellij_module.lookup_self(attr, intellij_provider.CcToolchainInfo)
    if not toolchain_info:
        return None

    return intellij_module.result(None, cross_target_internal_value = toolchain_info)

def _implementation(target, ctx, attr):
    forward = _forward_toolchain(target, ctx, attr)

    if forward:
        return forward
    else:
        return _compilecommands(target, ctx, attr)

_aspect = intellij_module.aspect(
    provider = None,  # TODO: handle this
    implementation = _implementation,
    field = None,  # TODO: handle this
)

module = intellij_module.define(
    file = "compilecommands",
    aspect = _aspect,
    toolchains = [CC_TOOLCHAIN_TYPE],
    aspect_providers = [CcInfo],
    rulesets = ["CC"],
)
