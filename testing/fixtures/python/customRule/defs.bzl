# Copyright 2026 JetBrains s.r.o.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

load("@rules_python//python:defs.bzl", "PyInfo")

def _py_codegen_impl(ctx):
    """A custom rule that generates Python code and provides it via PyInfo.

    The srcs attribute holds the inputs of the generator, not Python sources.
    """
    output = ctx.actions.declare_file(ctx.label.name + ".py")
    ctx.actions.expand_template(
        template = ctx.files.srcs[0],
        output = output,
        substitutions = {},
    )

    transitive_sources = depset(
        [output],
        transitive = [dep[PyInfo].transitive_sources for dep in ctx.attr.deps],
    )

    return [
        DefaultInfo(
            files = depset([output]),
            runfiles = ctx.runfiles(files = [output]),
        ),
        PyInfo(transitive_sources = transitive_sources),
    ]

py_codegen = rule(
    implementation = _py_codegen_impl,
    attrs = {
        "srcs": attr.label_list(allow_files = True),
        "deps": attr.label_list(providers = [PyInfo]),
    },
)
