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

load("//common:platform.bzl", "platform")
load("//config:aspect.bzl", "intellij_aspect")

# To ensure that targets visited under different aspect configurations created by
# this rule do not cause write conflicts this transition enforces a unique
# bazel configuration for this aspect configuration.
def _aspect_transition_impl(_settings, _attr):
    return {"//command_line_option:platform_suffix": "intellij_aspect"}

_aspect_transition = transition(
    implementation = _aspect_transition_impl,
    inputs = [],
    outputs = ["//command_line_option:platform_suffix"],
)

def _intellij_aspect_build_impl(ctx):
    info_files = [
        getattr(dep[OutputGroupInfo], "intellij-info", depset())
        for dep in ctx.attr.deps
    ]

    return [DefaultInfo(files = depset(transitive = info_files))]

_intellij_aspect_build = rule(
    implementation = _intellij_aspect_build_impl,
    attrs = {
        "deps": attr.label_list(
            aspects = [intellij_aspect],
            doc = "The targets to apply the IntelliJ aspect to.",
            cfg = _aspect_transition,
        )
    },
)

# derived from: https://github.com/bazelbuild/bazel-skylib/blob/main/rules/build_test.bzl
def _build_test_impl(ctx):
    extension = ".bat" if platform.is_windows() else ".sh"
    content = "exit 0" if platform.is_windows() else "#!/usr/bin/env bash\nexit 0"

    executable = ctx.actions.declare_file(ctx.label.name + extension)
    ctx.actions.write(output = executable, is_executable = True, content = content)

    return [DefaultInfo(
        files = depset([executable]),
        executable = executable,
        runfiles = ctx.runfiles(files = ctx.files.targets),
    )]

_build_test = rule(
    implementation = _build_test_impl,
    test = True,
    attrs = {"targets": attr.label_list(mandatory = True)},
)

def intellij_aspect_test(name, deps, tags = [], **kwargs):
    """Asserts the IntelliJ aspect builds successfully over the given deps.

    Applies the aspect to each language's deps and wraps the result in a build
    test, so `bazel test` passes if and only if the aspect builds cleanly. Only
    the languages you pass are exercised, so you only need the rule sets and
    toolchains for those languages.

    Args:
        name: Name of the test target.
        deps: List of targets to apply the aspect to.
        **kwargs: Passed through to the underlying test (e.g. tags, visibility, size).
    """
    build = "%s_build" % name

    _intellij_aspect_build(
        name = build,
        deps = deps,
        testonly = True,
        tags = ["manual", "no-ide"],
    )

    _build_test(
        name = name,
        targets = [build],
        tags = tags + (["no-ide"] if not "no-ide" in tags else []),
        **kwargs
    )
