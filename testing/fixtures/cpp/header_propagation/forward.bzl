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

load("@rules_cc//cc:defs.bzl", "CcInfo", "cc_common")

def _forward_impl(ctx):
    compilation_context = cc_common.merge_compilation_contexts(
        compilation_contexts = [ctx.attr.exported[CcInfo].compilation_context],
    )
    return [CcInfo(compilation_context = compilation_context)]

forward_cc = rule(
    implementation = _forward_impl,
    attrs = {
        "exported": attr.label(providers = [CcInfo]),
        "deps": attr.label_list(providers = [CcInfo]),
    },
    provides = [CcInfo],
)
