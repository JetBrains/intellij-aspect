# Copyright 2026 JetBrains s.r.o. and contributors.
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

load("@bazel_skylib//lib:paths.bzl", "paths")
load("@rules_pkg//pkg:providers.bzl", "PackageFilesInfo")
load("@rules_pkg//pkg:tar.bzl", "pkg_tar")
load("//testing/rules/lib:config.bzl", "TestMatrix", "merge_matrixes", "serialize_test_config")

def _config_name(config):
    """
    A user friendly name for the configuration, including bazel and module
    versions.
    """
    parts = ["bazel:%s" % config.bazel.version, "deploy:%s" % config.aspect_deployment] + [
        "%s:%s" % (name, version)
        for (name, version) in config.modules.items()
    ]

    return "[%s]" % ", ".join(parts)

def _config_hash(config):
    """
    Generates a unique hash for the configuration. Used to generate unique file
    names for every fixture.
    """
    parts = [config.bazel.version]
    parts.extend(["%s:%s" % (name, version) for (name, version) in config.modules.items()])
    parts.append(config.aspect_deployment)
    parts.extend(config.aspects)

    return hash(".".join(parts))

def _test_fixture_impl(ctx):
    outputs = []

    matrix = merge_matrixes([it[TestMatrix] for it in ctx.attr.configs])

    for config in matrix.configs:
        output = ctx.actions.declare_file("%s-%s.intellij-aspect-fixture" % (ctx.label.name, _config_hash(config)))

        input = proto.encode_text(struct(
            output_proto = output.path,
            project_archive = ctx.file.project.path,
            cache_archive = ctx.file.repo_cache.path,
            aspect_bcr_archive = ctx.file._aspect_bcr.path,
            aspect_ide_archive = ctx.file._aspect_ide.path,
            bcr_archive = ctx.file._bcr.path,
            config = serialize_test_config(config),
            targets = ctx.attr.targets,
        ))

        ctx.actions.run(
            inputs = [
                ctx.file.project,
                ctx.file.repo_cache,
                ctx.file._aspect_bcr,
                ctx.file._aspect_ide,
                ctx.file._bcr,
                config.bazel.executable,
            ],
            outputs = [output],
            executable = ctx.executable._builder,
            arguments = [input],
            mnemonic = "FixtureBuilder",
            progress_message = "Building test fixture for %{label} " + _config_name(config),
            use_default_shell_env = True,
        )

        outputs.append(output)

    return [DefaultInfo(files = depset(outputs))]

test_fixture = rule(
    attrs = {
        "project": attr.label(
            allow_single_file = [".zip"],
            mandatory = True,
        ),
        "repo_cache": attr.label(
            allow_single_file = [".zip"],
            mandatory = True,
        ),
        "configs": attr.label_list(
            providers = [TestMatrix],
            mandatory = True,
        ),
        "targets": attr.string_list(
            mandatory = True,
            doc = "list of targets to build for the fixture; do not us patterns",
        ),
        "_aspect_bcr": attr.label(
            allow_single_file = [".zip"],
            default = Label("//:archive_test"),
        ),
        "_aspect_ide": attr.label(
            allow_single_file = [".zip"],
            default = Label("//:archive_ide"),
        ),
        "_bcr": attr.label(
            allow_single_file = [".zip"],
            default = Label("@bcr_archive//:bcr.zip"),
        ),
        "_builder": attr.label(
            cfg = "exec",
            executable = True,
            default = Label("//testing/rules/fixture:builder_bin"),
        ),
    },
    implementation = _test_fixture_impl,
)
