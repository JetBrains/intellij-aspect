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

load("//common:artifact_location.bzl", "artifact_location")

def _java_resource_jar_impl(ctx):
    args = ctx.actions.args()
    args.add("c")
    args.add(ctx.outputs.jar)

    entries = {}
    for file in ctx.files.srcs:
        path = artifact_location.from_file(file).relative_path
        if path in entries:
            fail("conflicting resources for %s: %s and %s" % (path, entries[path].path, file.path))

        entries[path] = file
        args.add("%s=%s" % (path, file.path))

    ctx.actions.run(
        inputs = ctx.files.srcs,
        outputs = [ctx.outputs.jar],
        executable = ctx.executable._zipper,
        arguments = [args],
        mnemonic = "JavaResourceJar",
        progress_message = "Creating resource jar %{output}",
    )

    return [DefaultInfo(files = depset([ctx.outputs.jar]))]

java_resource_jar = rule(
    implementation = _java_resource_jar_impl,
    attrs = {
        "srcs": attr.label_list(
            allow_files = True,
            mandatory = True,
            doc = "files to package into the jar",
        ),
        "_zipper": attr.label(
            default = Label("@bazel_tools//tools/zip:zipper"),
            cfg = "exec",
            executable = True,
        ),
    },
    outputs = {
        "jar": "%{name}.jar",
    },
)
