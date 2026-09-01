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

load("//common:output_groups.bzl", "intellij_output_groups")
load("//config:aspect.bzl", "intellij_aspect")

_OUTPUT_GROUPS = [
    intellij_output_groups.INFO,
    intellij_output_groups.SYNC,
    intellij_output_groups.BUILD,
]

def _implementation(ctx):
    outputs = {}
    for group in _OUTPUT_GROUPS:
        transitive = [
            getattr(dep[OutputGroupInfo], group, depset())
            for dep in ctx.attr.deps
            if OutputGroupInfo in dep
        ]
        if transitive:
            outputs[group] = depset(transitive = transitive)

    return [OutputGroupInfo(**outputs)]

intellij_aspect_aggregate = rule(
    doc = "Applies the IntelliJ aspect to many roots behind one completion boundary.",
    implementation = _implementation,
    attrs = {
        "deps": attr.label_list(
            aspects = [intellij_aspect],
            doc = "Explicit target labels to apply the IntelliJ aspect to.",
        ),
    },
)
