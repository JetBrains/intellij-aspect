# Copyright 2025 JetBrains s.r.o.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

load("//private/repos:bazel_versions.bzl", "bazel_versions")
load("//private/repos:bcr_archive.bzl", "bcr_archive")

_bazel = tag_class(attrs = {
    "versions": attr.string_list(),
})

_bcr = tag_class(attrs = {
    "commit": attr.string(mandatory = True),
    "sha256": attr.string(mandatory = True),
})

def _collect_bazel_versions(mctx):
    return [
        version
        for mod in mctx.modules
        for tag in mod.tags.bazel
        for version in tag.versions
    ]

def _collect_bcr_config(mctx):
    for mod in mctx.modules:
        for tag in mod.tags.bcr:
            return struct(commit = tag.commit, sha256 = tag.sha256)
    return None

def _bazel_registry_impl(mctx):
    bazel_versions(
        name = "bazel_versions",
        versions = _collect_bazel_versions(mctx),
    )

    bcr_config = _collect_bcr_config(mctx)
    if not bcr_config:
        fail("no bcr config provided")

    bcr_archive(
        name = "bcr_archive",
        commit = bcr_config.commit,
        sha256 = bcr_config.sha256,
    )

bazel_registry = module_extension(
    implementation = _bazel_registry_impl,
    tag_classes = {
        "bazel": _bazel,
        "bcr": _bcr,
    },
)
