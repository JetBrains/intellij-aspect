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

_ARCHIVE_URL = "{url}/archive/{commit}.zip"

_BUILD_FILE = """
package(default_visibility = ["//visibility:public"])

exports_files(["project.zip"])

filegroup(
    name = "project",
    srcs = ["project.zip"],
)
"""

def _github_archive_impl(rctx):
    url = _ARCHIVE_URL.format(url = rctx.attr.url.rstrip("/"), commit = rctx.attr.commit)

    # download zip WITHOUT extracting - consumers work on a private extracted copy
    rctx.download(
        url = url,
        output = "project.zip",
        sha256 = rctx.attr.sha256,
    )

    rctx.file("BUILD", _BUILD_FILE)

github_archive = repository_rule(
    implementation = _github_archive_impl,
    attrs = {
        "url": attr.string(
            mandatory = True,
            doc = "URL of the GitHub project, e.g. https://github.com/abseil/abseil-cpp",
        ),
        "commit": attr.string(
            mandatory = True,
            doc = "Git commit SHA or tag of the project to download",
        ),
        "sha256": attr.string(
            mandatory = True,
            doc = "SHA256 checksum of the downloaded zip file",
        ),
    },
    doc = "Downloads a GitHub project source archive as a zip file.",
)
