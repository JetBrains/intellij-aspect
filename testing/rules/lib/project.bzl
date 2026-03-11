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

load("@bazel_skylib//lib:paths.bzl", "paths")

def _realpath(base_path, file):
    """Calculates the real path of the file inside the project zip."""
    return paths.relativize(file.short_path.removesuffix(".fix"), base_path)

def _test_project_impl(ctx):
    base = "%s/%s" % (ctx.label.package, ctx.attr.strip_prefix or ctx.label.name)

    mapping = [
        "%s=%s" % (_realpath(base, file), file.path)
        for file in ctx.files.srcs
    ]

    archive = ctx.actions.declare_file(ctx.label.name + ".zip")
    ctx.actions.run(
        inputs = ctx.files.srcs,
        executable = ctx.executable._zipper,
        outputs = [archive],
        arguments = ["c", archive.path] + mapping,
    )

    return [DefaultInfo(files = depset([archive]))]

project_archive = rule(
    attrs = {
        "srcs": attr.label_list(
            allow_files = True,
            mandatory = True,
        ),
        "strip_prefix": attr.string(
            default = "",
            doc = "a directory prefix to strip from all files, defaults to the target's name",
        ),
        "_zipper": attr.label(
            cfg = "exec",
            default = Label("@bazel_tools//tools/zip:zipper"),
            executable = True,
        ),
    },
    implementation = _test_project_impl,
)
