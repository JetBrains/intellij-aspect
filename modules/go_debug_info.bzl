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
load("//common:debug_info.bzl", "intellij_debug_info")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

COMPILE_MNEMONICS = ["GoCompilePkg", "GoCompilePkgExternal"]
LINK_MNEMONICS = ["GoLink"]

def _collect_actions(target, mnemonics):
    actions = [intellij_debug_info.action_info(it) for it in target.actions if it.mnemonic in mnemonics]
    return [it for it in actions if it]

def _implementation(target, ctx, attr):
    # targets built under exec configuration are never linked into the debuggee
    if intellij_common.is_exec_configuration(ctx):
        return None

    compile_actions = _collect_actions(target, COMPILE_MNEMONICS)
    link_actions = _collect_actions(target, LINK_MNEMONICS)

    # rules_go does not provide GoInfo in older versions, so we just hardcode the action names
    if not compile_actions and not link_actions:
        return None

    return intellij_module.result(
        value = intellij_common.struct(
            compile_actions = compile_actions,
            link_actions = link_actions,
            compilation_mode = intellij_debug_info.compilation_mode(ctx),
        ),
    )

_aspect = intellij_module.aspect(
    provider = intellij_provider.GoDebugInfo,
    implementation = _implementation,
    field = "go_debug_info",
)

module = intellij_module.define(
    file = "go_debug_info",
    aspect = _aspect,
    rulesets = ["GO"],
)
