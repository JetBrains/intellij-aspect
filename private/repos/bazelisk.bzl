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

_BAZELISK_RELEASE_URL = "https://github.com/bazelbuild/bazelisk/releases/download/v{version}/bazelisk-{os}-{arch}{suffix}"

_BUILD_FILE = """
package(default_visibility = ["//visibility:public"])

exports_files(["{executable}"])

filegroup(
    name = "executable",
    srcs = ["{executable}"],
)
"""

def _os_name(rctx):
    """Normalize OS name for download URLs."""
    name = rctx.os.name.lower()
    if name.startswith("linux"):
        return "linux"
    if name.startswith("mac os"):
        return "darwin"
    if name.startswith("windows"):
        return "windows"
    fail("unrecognized os: %s" % name)

def _arch_name(rctx):
    """Normalize architecture name for download URLs."""
    arch = rctx.os.arch.lower()
    if arch.startswith("amd64") or arch.startswith("x86_64"):
        return "amd64"
    if arch.startswith("aarch64") or arch.startswith("arm"):
        return "arm64"
    fail("unrecognized arch: %s" % arch)

def _suffix(rctx):
    """Windows releases are suffixed with .exe"""
    if rctx.os.name.lower().startswith("windows"):
        return ".exe"
    else:
        return ""

def _bazelisk_impl(rctx):
    executable = "bazelisk" + _suffix(rctx)

    url = _BAZELISK_RELEASE_URL.format(
        version = rctx.attr.version,
        os = _os_name(rctx),
        arch = _arch_name(rctx),
        suffix = _suffix(rctx),
    )

    rctx.download(
        url = url,
        output = executable,
        sha256 = rctx.attr.sha256,
        executable = True,
    )

    rctx.file("BUILD", _BUILD_FILE.format(executable = executable))

bazelisk = repository_rule(
    implementation = _bazelisk_impl,
    attrs = {
        "version": attr.string(mandatory = True),
        "sha256": attr.string(),
    },
    doc = "Downloads the Bazelisk launcher for the host platform.",
)
