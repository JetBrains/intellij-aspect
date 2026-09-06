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

def _project_archives(ctx):
    """Returns the archives to extract as (archive, destination) pairs, the project first."""
    archives = [(ctx.file.project, "")]

    for destination, target in ctx.attr.overlays.items():
        if not destination or destination.startswith("/") or ".." in destination.split("/"):
            fail("overlay destination must be a relative path inside the project: '%s'" % destination)

        files = target.files.to_list()
        if len(files) != 1:
            fail("overlay '%s' must provide exactly one archive, got %d" % (destination, len(files)))

        archives.append((files[0], destination))

    return archives

def _extract_project(ctx):
    """Extracts the project zip, and any overlay zips, into a single tree artifact."""
    directory = ctx.actions.declare_directory(ctx.label.name + "_project")
    archives = _project_archives(ctx)

    # bazel allows one action per artifact, so all archives are extracted by one command
    commands = ["set -e"]
    for archive, destination in archives:
        target = directory.path + "/" + destination if destination else directory.path
        commands.append("{unzip} {archive} {target} {strip_prefix}".format(
            unzip = ctx.executable._unzip.path,
            archive = archive.path,
            target = target,
            strip_prefix = ctx.attr.strip_prefix,
        ))

    ctx.actions.run_shell(
        inputs = [archive for archive, _ in archives],
        outputs = [directory],
        # the FilesToRunProvider carries the runfiles the java_binary launcher needs
        tools = [ctx.attr._unzip[DefaultInfo].files_to_run],
        command = "\n".join(commands),
        mnemonic = "ExtractProject",
        progress_message = "Extracting project for %{label}",
    )

    return directory

def _measure_report_impl(ctx):
    project = _extract_project(ctx)
    report = ctx.actions.declare_file(ctx.label.name + ".textproto")

    args = ctx.actions.args()
    args.add(project.path)
    args.add("--target", ctx.attr.target)
    args.add("--languages", ",".join(ctx.attr.languages))
    args.add("--repeat", str(ctx.attr.repeats))
    args.add("--report", report)
    args.add("--bazel_version", ctx.attr.bazel_version)
    args.add("--quiet")

    if ctx.attr.nobuild:
        args.add("--nobuild")

    ctx.actions.run(
        inputs = [project],
        outputs = [report],
        executable = ctx.executable._measure,
        arguments = [args],
        mnemonic = "MeasureAspect",
        progress_message = "Measuring aspect overhead for %{label}",
        execution_requirements = {
            "requires-network": "1",
            "no-cache": "1",
            "no-remote": "1",
        },
    )

    return [DefaultInfo(files = depset([report]))]

measure_report = rule(
    attrs = {
        "project": attr.label(
            allow_single_file = [".zip"],
            mandatory = True,
        ),
        "overlays": attr.string_keyed_label_dict(
            allow_files = [".zip"],
            doc = "archives to extract into a directory of the project, keyed by that directory",
        ),
        "strip_prefix": attr.int(
            default = 1,
            doc = "path segments to strip when extracting, GitHub archives have one top-level directory",
        ),
        "target": attr.string(default = "//..."),
        "languages": attr.string_list(mandatory = True),
        "repeats": attr.int(default = 3),
        "bazel_version": attr.string(default = "9.2.0"),
        "nobuild": attr.bool(default = False),
        "_measure": attr.label(
            cfg = "exec",
            executable = True,
            default = Label("//tools/measure:measure"),
        ),
        "_unzip": attr.label(
            cfg = "exec",
            executable = True,
            default = Label("//tools/unzip"),
        ),
    },
    implementation = _measure_report_impl,
)
