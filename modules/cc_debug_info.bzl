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

load("@rules_cc//cc:defs.bzl", "CcInfo", "DebugPackageInfo")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:output_groups.bzl", "intellij_output_groups")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

COMPILE_MNEMONICS = ["CppCompile"]
LINK_MNEMONICS = ["CppLink"]

# output groups of the cc rules that hold files the debugger reads
DEBUG_OUTPUT_GROUPS = ["compilation_outputs", "archive", "dsyms", "pdb_file"]

# CompilationMode enum; must match CcDebugInfo.CompilationMode
COMPILATION_MODE_FASTBUILD = 0
COMPILATION_MODE_DBG = 1
COMPILATION_MODE_OPT = 2

COMPILATION_MODE_MAPPING = {
    "fastbuild": COMPILATION_MODE_FASTBUILD,
    "dbg": COMPILATION_MODE_DBG,
    "opt": COMPILATION_MODE_OPT,
}

def _aspect_guard(target, ctx):
    """Returns true if the aspect should be applied to the current target."""
    if CcInfo not in target:
        return False

    # go targets always provide CcInfo, usually it's empty and even if it isn't we don't handle it
    if ctx.rule.kind.startswith("go_"):
        return False

    # targets built under exec configuration are never linked into the debuggee
    if intellij_common.is_exec_configuration(ctx):
        return False

    return True

def _is_header_token(file):
    """Returns true if the file is a header token produced by parse_headers or process_headers_in_dependencies."""
    return file.basename.endswith(".processed")

def _action_info(action):
    """Converts an action to a CcDebugInfo.Action. Returns None if the command line is not available."""
    if action.argv == None:
        return None

    return intellij_common.struct(
        mnemonic = action.mnemonic,
        arguments = action.argv,
        outputs = artifact_location.from_files(action.outputs.to_list()),
    )

def _collect_actions(target, mnemonics):
    # header parsing actions share the CppCompile mnemonic, but their header tokens are not read by the debugger
    actions = [
        _action_info(it)
        for it in target.actions
        if it.mnemonic in mnemonics and not any([_is_header_token(f) for f in it.outputs.to_list()])
    ]

    return [it for it in actions if it]

def _collect_debug_files(target):
    """Collects the target's own files that the debugger reads."""
    files = []

    if OutputGroupInfo in target:
        groups = target[OutputGroupInfo]
        for name in DEBUG_OUTPUT_GROUPS:
            if hasattr(groups, name):
                # header tokens are not read by the debugger
                files.extend([f for f in getattr(groups, name).to_list() if not _is_header_token(f)])

    if DebugPackageInfo in target and target[DebugPackageInfo].dwp_file:
        files.append(target[DebugPackageInfo].dwp_file)

    return files

def _implementation(target, ctx, attr):
    if not _aspect_guard(target, ctx):
        return None

    compilation_mode = COMPILATION_MODE_MAPPING.get(ctx.var["COMPILATION_MODE"], COMPILATION_MODE_FASTBUILD)

    return intellij_module.result(
        outputs = {intellij_output_groups.DEBUG: intellij_common.depset(_collect_debug_files(target))},
        value = intellij_common.struct(
            compile_actions = _collect_actions(target, COMPILE_MNEMONICS),
            link_actions = _collect_actions(target, LINK_MNEMONICS),
            # proto3 enum default values don't need to be encoded
            compilation_mode = None if compilation_mode == 0 else compilation_mode,
        ),
    )

_aspect = intellij_module.aspect(
    provider = intellij_provider.CcDebugInfo,
    implementation = _implementation,
    field = "cc_debug_info",
)

module = intellij_module.define(
    file = "cc_debug_info",
    aspect = _aspect,
    aspect_providers = [CcInfo],
    rulesets = ["CC"],
)
