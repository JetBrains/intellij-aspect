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

load("@rules_java//java:defs.bzl", "JavaInfo")

def _java_api_dump_impl(ctx):
    infos = [dep[JavaInfo] for dep in ctx.attr.deps]

    # the jars that make up the API: the targets themselves and everything they export
    jars = depset(transitive = [info.full_compile_jars for info in infos])

    # everything required to load the classes of the API jars, interface jars are not
    # usable here since the JVM rejects their stripped method bodies when linking
    classpath = depset(transitive = [info.transitive_runtime_jars for info in infos])

    output = ctx.actions.declare_file("%s.txt" % ctx.label.name)

    args = ctx.actions.args()
    args.add(output, format = "--output=%s")
    args.add_all(jars, format_each = "--jar=%s")
    args.add_all(classpath, format_each = "--classpath=%s")
    args.add_all(ctx.attr.exclude_packages, format_each = "--exclude=%s")

    ctx.actions.run(
        executable = ctx.executable._tool,
        arguments = [args],
        inputs = depset(transitive = [jars, classpath]),
        outputs = [output],
        mnemonic = "JavaApiDump",
        progress_message = "Dumping Java API of %{label}",
    )

    return [DefaultInfo(files = depset([output]))]

java_api_dump = rule(
    implementation = _java_api_dump_impl,
    doc = "Dumps all public and protected symbols of the given Java targets, including their exports.",
    attrs = {
        "deps": attr.label_list(
            mandatory = True,
            providers = [JavaInfo],
            doc = "Java targets whose API should be dumped.",
        ),
        "exclude_packages": attr.string_list(
            doc = "Packages to leave out of the dump, including their sub packages.",
        ),
        "_tool": attr.label(
            default = Label("//tools/apidump"),
            executable = True,
            cfg = "exec",
        ),
    },
)
