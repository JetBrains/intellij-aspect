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
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

# rule kinds to include in outputs, even though no special information is available
EXTRA_RULES = [
    "sh_binary",
    "sh_library",
    "genrule",
    "intellij_plugin_debug_target",
    "_plugin_deploy_zip",
    "intellij_plugin_zip",
]

def _implementation(target, ctx, attr):
    if not ctx.rule.kind in EXTRA_RULES:
        return None

    return intellij_module.result(intellij_common.struct(is_extra = True))

_aspect = intellij_module.aspect(
    provider = intellij_provider.ExtraRulesInfo,
    implementation = _implementation,
    field = "extra_rule",
)

module = intellij_module.define(
    file = "extra_rules_info",
    aspect = _aspect,
)
