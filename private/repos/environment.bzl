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

def _environment_impl(rctx):
    variables = ",".join(["'%s': '%s'" % (var, _fmt_value(rctx.getenv(var))) for var in rctx.attr.vars])
    rctx.file("environment.bzl", "environment = {%s}" % variables)
    rctx.file("BUILD", "exports_files=['environment.bzl']")

def _fmt_value(value):
    if not value:
        return ""

    return value.replace("\\", "\\\\").replace("'", "\\'")

environment = repository_rule(
    implementation = _environment_impl,
    attrs = {
        "vars": attr.string_list(
            mandatory = True,
            doc = "List of environment variables to expose.",
        ),
    },
    doc = "Wrapper repository to make environment variables accessible without using default_shell_env.",
)
