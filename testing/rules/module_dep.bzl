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

TestModuleDep = provider(
    doc = "A Bazel module dependency with optional MODULE.bazel configuration.",
    fields = {
        "name": "str - The module name as used in bazel_dep().",
        "version": "str - The module version.",
        "config": "str - Optional extra MODULE.bazel content (extensions, register_toolchains).",
        "flags": "list[str] - Optional extra .bazelrc lines i.e. flags.",
    },
)

def _test_module_dep_impl(ctx):
    return [TestModuleDep(
        name = ctx.attr.module_name or ctx.label.name,
        version = ctx.attr.version,
        config = ctx.attr.config,
        flags = ctx.attr.flags,
    )]

test_module_dep = rule(
    implementation = _test_module_dep_impl,
    doc = "Declares a Bazel module dependency for use in test fixtures.",
    attrs = {
        "module_name": attr.string(
            doc = "The module name as used in bazel_dep(). Defaults to the target name if not specified.",
        ),
        "version": attr.string(
            mandatory = True,
            doc = "The module version.",
        ),
        "config": attr.string(
            default = "",
            doc = "Optional extra MODULE.bazel content appended after the bazel_dep line (e.g., use_extension, register_toolchains).",
        ),
        "flags": attr.string_list(
            default = [],
            doc = "Optional extra .bazelrc lines i.e. flags.",
        ),
    },
    provides = [TestModuleDep],
)
