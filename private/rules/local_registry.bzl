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

def _local_registry(ctx):
    out = ctx.actions.declare_file("%s.tar" % (ctx.label.name,))
    ctx.actions.run(
        outputs = [out],
        inputs = [ctx.file.archive, ctx.file.module_file],
        executable = ctx.executable._local_registry,
        arguments = [ctx.file.archive.path, ctx.file.module_file.path, out.path, ctx.attr.module_version, ctx.attr.module_name],
    )
    return [DefaultInfo(files = depset([out]))]

local_registry = rule(
    implementation = _local_registry,
    attrs = {
        "archive": attr.label(mandatory = True, allow_single_file = True),
        "module_file": attr.label(mandatory = True, allow_single_file = True),
        "module_name": attr.string(mandatory = True),
        "module_version": attr.string(mandatory = True),
        "_local_registry": attr.label(default = "//private/rules:local_registry", executable = True, cfg = "exec"),
    },
)
