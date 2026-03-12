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

load("@rules_pkg//pkg:providers.bzl", "PackageFilesInfo")

def _aspect_lib_impl(ctx):
    map = {
        "%s/%s" % (ctx.label.package, file.basename): file
        for dep in ctx.attr.files
        for file in dep[DefaultInfo].files.to_list()
    }

    # generate an empty build file
    build_file = ctx.actions.declare_file("BUILD.bazel")
    ctx.actions.write(build_file, "# generated BUILD file")

    map["%s/BUILD" % ctx.label.package] = build_file

    return [
        DefaultInfo(files = depset([build_file] + map.values())),
        PackageFilesInfo(dest_src_map = map),
    ]

aspect_lib = rule(
    implementation = _aspect_lib_impl,
    attrs = {"files": attr.label_list(allow_files = True)},
)
