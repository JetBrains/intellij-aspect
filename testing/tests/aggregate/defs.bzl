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

_OUTPUT_GROUPS = [
    intellij_output_groups.INFO,
    intellij_output_groups.SYNC,
    intellij_output_groups.BUILD,
]

def _fake_output_groups_impl(ctx):
    outputs = {}
    for group in _OUTPUT_GROUPS:
        output = ctx.actions.declare_file("%s.%s" % (ctx.label.name, group))
        ctx.actions.write(output, group)
        outputs[group] = depset([output])

    return [OutputGroupInfo(**outputs)]

fake_output_groups = rule(implementation = _fake_output_groups_impl)

def _aggregate_output_groups_test_impl(ctx):
    output_groups = ctx.attr.target[OutputGroupInfo]
    errors = []

    for group in _OUTPUT_GROUPS:
        actual = sorted([file.basename for file in getattr(output_groups, group, depset()).to_list()])
        expected = sorted(["%s.%s" % (root, group) for root in ctx.attr.expected_roots])
        if actual != expected:
            errors.append("%s: expected %s, got %s" % (group, expected, actual))

    return [AnalysisTestResultInfo(
        success = not errors,
        message = "\n".join(errors),
    )]

aggregate_output_groups_test = rule(
    implementation = _aggregate_output_groups_test_impl,
    analysis_test = True,
    attrs = {
        "target": attr.label(
            mandatory = True,
            providers = [OutputGroupInfo],
        ),
        "expected_roots": attr.string_list(),
    },
)

def _aggregate_output_groups_nonempty_test_impl(ctx):
    output_groups = ctx.attr.target[OutputGroupInfo]
    empty = [
        group
        for group in ctx.attr.groups
        if not getattr(output_groups, group, depset()).to_list()
    ]

    return [AnalysisTestResultInfo(
        success = not empty,
        message = "empty output groups: %s" % empty,
    )]

aggregate_output_groups_nonempty_test = rule(
    implementation = _aggregate_output_groups_nonempty_test_impl,
    analysis_test = True,
    attrs = {
        "target": attr.label(
            mandatory = True,
            providers = [OutputGroupInfo],
        ),
        "groups": attr.string_list(mandatory = True),
    },
)
