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

load("@protobuf//bazel/common:proto_common.bzl", "proto_common")
load("@protobuf//bazel/common:proto_info.bzl", "ProtoInfo")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:provider.bzl", "intellij_provider")
load("//common:third_party/proto_common.bzl", "fallback_get_import_path")
load(":module.bzl", "intellij_module")

def _get_import_path(proto_file):
    if hasattr(proto_common, "get_import_path"):
        return proto_common.get_import_path(proto_file)
    return fallback_get_import_path(proto_file)

def _source_mappings(target):
    mappings = []
    for source in target[ProtoInfo].direct_sources:
        proto = intellij_common.struct(
            import_path = _get_import_path(source),
            proto_file = artifact_location.from_file(source),
        )
        mappings.append(proto)
    return mappings

def _implementation(target, ctx, attr):
    if not ProtoInfo in target:
        return None

    return intellij_module.result(
        intellij_common.struct(
            source_mappings = _source_mappings(target),
        ),
    )

_aspect = intellij_module.aspect(
    provider = intellij_provider.ProtobufInfo,
    implementation = _implementation,
)

module = intellij_module.define(
    file = "protobuf_info",
    aspect = _aspect,
    rulesets = ["PROTO"],
)
