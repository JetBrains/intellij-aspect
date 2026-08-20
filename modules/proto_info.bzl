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

_PROTO_MODULES = [
    intellij_provider.LegacyRulesProtoInfo,
    intellij_provider.ProtobufInfo,
]

def _implementation(target, ctx, attr):
    for it in _PROTO_MODULES:
        contributor = intellij_module.lookup_self(attr, it)
        if contributor:
            return intellij_module.result(contributor.value)

    return None

_aspect = intellij_module.aspect(
    provider = intellij_provider.ProtoInfo,
    implementation = _implementation,
    field = "protobuf_target_info",
)

module = intellij_module.define(
    file = "proto_info",
    aspect = _aspect,
    rulesets = ["@protobuf", "@rules_proto"],
)
