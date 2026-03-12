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

BazelBinary = provider(
    doc = "Host Bazel executable and version.",
    fields = {
        "version": "str - Bazel release string (e.g., 8.4.2).",
        "executable": "File - Bazel executable used for running builds.",
    },
)

def _bazel_binary_impl(ctx):
    return [
        BazelBinary(
            version = ctx.attr.version,
            executable = ctx.file.executable,
        ),
        DefaultInfo(files = depset([ctx.file.executable])),
    ]

bazel_binary = rule(
    implementation = _bazel_binary_impl,
    attrs = {
        "version": attr.string(mandatory = True),
        "executable": attr.label(
            allow_single_file = True,
            cfg = "exec",
            executable = True,
        ),
    },
    provides = [BazelBinary],
)
