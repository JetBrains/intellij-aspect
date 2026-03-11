# Copyright 2026 JetBrains s.r.o. and contributors.
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

_BCR_ARCHIVE_URL = "https://github.com/bazelbuild/bazel-central-registry/archive/{commit}.zip"

_BUILD_FILE = """
package(default_visibility = ["//visibility:public"])

exports_files(["bcr.zip"])

filegroup(
    name = "bcr",
    srcs = ["bcr.zip"],
)
"""

def _bcr_archive_impl(rctx):
    url = _BCR_ARCHIVE_URL.format(commit = rctx.attr.commit)

    # Download zip WITHOUT extracting - used for offline builds
    rctx.download(
        url = url,
        output = "bcr.zip",
        sha256 = rctx.attr.sha256,
    )

    rctx.file("BUILD", _BUILD_FILE)

bcr_archive = repository_rule(
    implementation = _bcr_archive_impl,
    attrs = {
        "commit": attr.string(
            mandatory = True,
            doc = "Git commit SHA of the BCR to download",
        ),
        "sha256": attr.string(
            mandatory = True,
            doc = "SHA256 checksum of the downloaded zip file",
        ),
    },
    doc = "Downloads a specific BCR commit as a zip file for offline builds.",
)
