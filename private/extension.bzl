# Copyright 2026 JetBrains s.r.o.
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

load("//private/repos:bazelisk.bzl", "bazelisk")
load("//private/repos:bcr_archive.bzl", "bcr_archive")
load("//private/repos:environment.bzl", "environment")

_bazelisk = tag_class(attrs = {
    "version": attr.string(mandatory = True),
    "sha256": attr.string(),
})

_bcr = tag_class(attrs = {
    "commit": attr.string(mandatory = True),
    "sha256": attr.string(mandatory = True),
})

def _collect_bazelisk_config(mctx):
    for mod in mctx.modules:
        for tag in mod.tags.bazelisk:
            return struct(version = tag.version, sha256 = tag.sha256)
    return None

def _collect_bcr_config(mctx):
    for mod in mctx.modules:
        for tag in mod.tags.bcr:
            return struct(commit = tag.commit, sha256 = tag.sha256)
    return None

def _bazel_registry_impl(mctx):
    bazelisk_config = _collect_bazelisk_config(mctx)
    if not bazelisk_config:
        fail("no bazelisk config provided")

    bazelisk(
        name = "bazelisk",
        version = bazelisk_config.version,
        sha256 = bazelisk_config.sha256,
    )

    bcr_config = _collect_bcr_config(mctx)
    if not bcr_config:
        fail("no bcr config provided")

    bcr_archive(
        name = "bcr_archive",
        commit = bcr_config.commit,
        sha256 = bcr_config.sha256,
    )

    environment(
        name = "bazel_env",
        vars = ["BAZEL_SH"],
    )

bazel_registry = module_extension(
    implementation = _bazel_registry_impl,
    tag_classes = {
        "bazelisk": _bazelisk,
        "bcr": _bcr,
    },
)
