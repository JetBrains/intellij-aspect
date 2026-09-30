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

load("@rules_foreign_cc//foreign_cc:providers.bzl", "ForeignCcDepsInfo")
load("//common:common.bzl", "intellij_common")
load("//common:output_groups.bzl", "intellij_output_groups")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

def _implementation(target, ctx, attr):
    if not ForeignCcDepsInfo in target:
        return None

    return intellij_module.result(
        value = intellij_common.struct(
            is_foreign = True,
        ),
        outputs = {
            intellij_output_groups.BUILD: target[DefaultInfo].files,
        },
    )

_aspect = intellij_module.aspect(
    provider = intellij_provider.ForeignCcInfo,
    implementation = _implementation,
    field = "foreign_cc",
)

module = intellij_module.define(
    file = "foreign_cc_info",
    aspect = _aspect,
    rulesets = ["FOREIGN_CC"],
)
