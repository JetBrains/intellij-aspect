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

load("@bazel_env//:environment.bzl", _bazel_env = "environment")
load("@bazel_skylib//rules:common_settings.bzl", "BuildSettingInfo")
load(":config.bzl", "TestMatrix", "config_hash", "config_name", "merge_matrixes", "serialize_test_config")

def _test_fixture_impl(ctx):
    output_protos = []
    profile_files = []

    matrix = merge_matrixes([it[TestMatrix] for it in ctx.attr.configs])

    for config in matrix.configs:
        unique_hash = config_hash(config)

        output_proto = ctx.actions.declare_file("%s-%s.intellij-aspect-fixture" % (ctx.label.name, unique_hash))
        profile_file = ctx.actions.declare_file("%s-%s_profile.gz" % (ctx.label.name, unique_hash))

        worker_options = proto.encode_text(struct(
            bazelisk = ctx.file._bazelisk.path,
            registry_file = ctx.file._registry_file.path,
            max_servers = ctx.attr._max_servers[BuildSettingInfo].value,
            repo_cache = ctx.attr._repo_cache[BuildSettingInfo].value,
        ))

        work_arguments = proto.encode_text(struct(
            output_proto = output_proto.path,
            output_profile = profile_file.path,
            project_archive = ctx.file.project.path,
            aspect_bcr_archive = ctx.file._aspect_bcr.path,
            aspect_ide_archive = ctx.file._aspect_ide.path,
            config = serialize_test_config(config),
            targets = ctx.attr.targets,
            output_groups = ctx.attr.output_groups,
        ))

        response_file = ctx.actions.declare_file("%s-%s_work_arguments.textproto" % (ctx.label.name, unique_hash))
        ctx.actions.write(response_file, work_arguments)

        flagfile = ctx.actions.declare_file("%s-%s_flagfile" % (ctx.label.name, unique_hash))
        ctx.actions.write(flagfile, "PROTO:%s\n" % response_file.path)

        env = {}

        if ctx.attr.use_msys2:
            env["BAZEL_SH"] = _bazel_env.get("BAZEL_SH", default = "")

        ctx.actions.run(
            inputs = [
                flagfile,
                response_file,
                ctx.file._bazelisk,
                ctx.file._registry_file,
                ctx.file.project,
                ctx.file._aspect_bcr,
                ctx.file._aspect_ide,
            ],
            executable = ctx.executable._builder,
            arguments = [worker_options, "@" + flagfile.path],
            outputs = [output_proto, profile_file],
            mnemonic = "FixtureBuilder",
            progress_message = "Building test fixture for %{label} " + config_name(config),
            execution_requirements = {
                "supports-multiplex-workers": "1",
                "requires-worker-protocol": "proto",
                "requires-network": "1",
            },
            env = env,
        )

        output_protos.append(output_proto)
        profile_files.append(profile_file)

    return [
        DefaultInfo(files = depset(output_protos)),
        OutputGroupInfo(debug = depset(profile_files)),
    ]

test_fixture = rule(
    attrs = {
        "project": attr.label(
            allow_single_file = [".zip"],
            mandatory = True,
        ),
        "configs": attr.label_list(
            providers = [TestMatrix],
            mandatory = True,
        ),
        "targets": attr.string_list(
            mandatory = True,
            doc = "list of targets to build for the fixture; do not use patterns",
        ),
        "output_groups": attr.string_list(
            doc = "list of additional output groups to request",
        ),
        "use_msys2": attr.bool(
            default = False,
            doc = "whether to enable MSYS2 when building on Windows",
        ),
        "_aspect_bcr": attr.label(
            allow_single_file = [".zip"],
            default = Label("//:archive_test"),
        ),
        "_aspect_ide": attr.label(
            allow_single_file = [".zip"],
            default = Label("//:archive_ide"),
        ),
        "_registry_file": attr.label(
            allow_single_file = [".zip"],
            default = Label("@bcr_archive//:bcr.zip"),
        ),
        "_bazelisk": attr.label(
            allow_single_file = True,
            cfg = "exec",
            default = Label("@bazelisk//:executable"),
        ),
        "_builder": attr.label(
            cfg = "exec",
            executable = True,
            default = Label("//testing/rules/worker:builder_bin"),
        ),
        "_max_servers": attr.label(
            default = Label("//testing/rules:max_servers"),
        ),
        "_repo_cache": attr.label(
            default = Label("//testing/rules:repo_cache"),
        ),
    },
    implementation = _test_fixture_impl,
)
