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

load("@rules_proto//proto:defs.bzl", "ProtoInfo", "proto_common")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load(":provider.bzl", "intellij_provider")

def _source_mappings(target):
    mappings = []
    for source in target[ProtoInfo].direct_sources:
        proto = intellij_common.struct(
            import_path = proto_common.get_import_path(source),
            proto_file = artifact_location.from_file(source),
        )
        mappings.append(proto)
    return mappings

def _aspect_impl(target, ctx):
    if not ProtoInfo in target:
        return [intellij_provider.LegacyRulesProtoInfo(present = False)]
    return [
        intellij_provider.create(
            ctx = ctx,
            provider = intellij_provider.LegacyRulesProtoInfo,
            value = intellij_common.struct(
                source_mappings = _source_mappings(target),
            ),
        ),
    ]

intellij_rules_proto_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.LegacyRulesProtoInfo],
)
