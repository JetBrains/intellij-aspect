load("@rules_cc//cc:defs.bzl", "CcInfo", "cc_common")
load("@rules_cc//cc:find_cc_toolchain.bzl", "CC_TOOLCHAIN_TYPE")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:dependencies.bzl", "intellij_deps")
load("//common:make_variables.bzl", "expand_make_variables")
load("//common:output_groups.bzl", "intellij_output_groups")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

OUTPUT_GROUP = "intellij-compilecommands"

EXECROOT_PLACEHOLDER = "{EXECROOT}"

CPP_EXTENSION = ["cpp", "cc", "cxx", "c++"]

C_EXTENSION = ["c"]

def _lookup_list(info, name):
    """Since we trim providers we need getattr for lookups."""
    return getattr(info, name, [])

def _compilecommands_entry(c_arguments, cpp_arguments, info, file):
    is_cpp = file.extension in CPP_EXTENSION

    rule_ctx = info.rule_context
    comp_ctx = info.compilation_context

    arguments = list(cpp_arguments if is_cpp else c_arguments)
    arguments.extend(_lookup_list(rule_ctx, "copts"))
    arguments.extend(_lookup_list(rule_ctx, "cxxopts" if is_cpp else "conlyopts"))

    for define in _lookup_list(comp_ctx, "defines"):
        arguments.append("-D%s" % define)

    for include in _lookup_list(comp_ctx, "includes"):
        arguments.append("-I%s" % include)

    for include in _lookup_list(comp_ctx, "quote_includes"):
        arguments.append("-iquote%s" % include)

    for include in _lookup_list(comp_ctx, "system_includes"):
        arguments.append("-isystem%s" % include)

    arguments.extend(["-c", file.path])

    return struct(
        directory = EXECROOT_PLACEHOLDER,
        arguments = arguments,
        file = file.path,
    )

def _find_toolchain_info(ctx):
    """Looks up the toolchain info forwarded by the resolved cc toolchain."""

    # toolchains attribute only available in Bazel 8+
    toolchains = getattr(ctx.rule, "toolchains", None)
    if not toolchains or CC_TOOLCHAIN_TYPE not in toolchains:
        return None

    return intellij_module.lookup_target(toolchains[CC_TOOLCHAIN_TYPE], intellij_provider.CompileCommandsInfo)

def _compilecommands(target, ctx, attr):
    """Creates the commpile commands json file and writes it."""
    target_result = intellij_module.lookup_self(attr, intellij_provider.CcInfo)
    if not target_result:
        return None

    toolchain_info = _find_toolchain_info(ctx)
    if not toolchain_info:
        return None

    target_info = target_result.value

    c_arguments = [toolchain_info.c_compiler] + toolchain_info.c_option
    cpp_arguments = [toolchain_info.cpp_compiler] + toolchain_info.cpp_option

    for include in toolchain_info.built_in_include_directory:
        c_arguments.append("-isystem%s" % include)
        cpp_arguments.append("-isystem%s" % include)

    if getattr(toolchain_info, "sysroot", None):
        c_arguments.append("--sysroot=%s" % toolchain_info.sysroot)
        cpp_arguments.append("--sysroot=%s" % toolchain_info.sysroot)

    commands = [
        _compilecommands_entry(c_arguments, cpp_arguments, target_info, file)
        for src in intellij_common.attr_as_label_list(ctx, "srcs")
        for file in src.files.to_list()
        if file.extension in CPP_EXTENSION + C_EXTENSION
    ]

    if not commands:
        return None

    template = ctx.actions.declare_file(target.label.name + ".compile_commands.json.tpl")
    ctx.actions.write(template, json.encode(commands))

    file = ctx.actions.declare_file(target.label.name + ".compile_commands.json")
    ctx.actions.run_shell(
        inputs = [template],
        outputs = [file],
        command = 'sed "s|%s|$PWD|g" "$1" > "$2"' % EXECROOT_PLACEHOLDER,
        arguments = [template.path, file.path],
        mnemonic = "IntelliJCompileCommands",
        progress_message = "Expanding compile commands for %{label}",
        # the action has to run directly in the execroot
        execution_requirements = {"local": "1"},
    )

    return intellij_module.result(None, outputs = {OUTPUT_GROUP: depset([file])})

def _forward_toolchain(target, ctx, attr):
    """Forward the toolchain info so it can be querried later."""
    toolchain_result = intellij_module.lookup_self(attr, intellij_provider.CcToolchainInfo)
    if not toolchain_result:
        return None

    return intellij_module.result(None, cross_target_internal_value = toolchain_result.value)

def _implementation(target, ctx, attr):
    forward = _forward_toolchain(target, ctx, attr)

    if forward:
        return forward
    else:
        return _compilecommands(target, ctx, attr)

_aspect = intellij_module.aspect(
    provider = intellij_provider.CompileCommandsInfo,
    implementation = _implementation,
    field = "compilecommands",  # unused, does not really matter
)

module = intellij_module.define(
    file = "compilecommands",
    aspect = _aspect,
    toolchains = [CC_TOOLCHAIN_TYPE],
    aspect_providers = [CcInfo],
    rulesets = ["CC"],
)
