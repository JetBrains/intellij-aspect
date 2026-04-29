# Copyright 2026 JetBrains s.r.o.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

load("@rules_python//python:defs.bzl", "PyInfo")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load(":provider.bzl", "intellij_provider")

TOOLCHAIN_TYPE = "@rules_python//python:toolchain_type"  # alias target pointing to the actual toolchain

def _get_runtime(ctx):
    if TOOLCHAIN_TYPE in ctx.toolchains:
        toolchain = ctx.toolchains[TOOLCHAIN_TYPE]
        if toolchain:
            return getattr(toolchain, "py3_runtime", struct())
    return struct()

def _source_files(ctx):
    def _files_to_list(source):
        files = source[DefaultInfo].files
        if files == None:
            return []
        else:
            return files.to_list()

    return [
        f
        for t in getattr(ctx.rule.attr, "srcs", [])
        for f in _files_to_list(t)
        if not f.is_directory
    ]

def _aspect_impl(target, ctx):
    if PyInfo not in target:
        return [intellij_provider.PythonInfo(present = False)]

    runtime = _get_runtime(ctx)

    imports = list(getattr(ctx.rule.attr, "imports", []))
    generated_sources = []
    if 0 == len(_source_files(ctx)):
        def provider_import_to_attr_import(provider_import):
            """\
            Remaps the imports from PyInfo

            The imports that are supplied on the `PyInfo` are relative to the runfiles and so are
            not the same as those which might be supplied on an attribute of `py_library`. This
            function will remap those back so they look as if they were `imports` attributes on
            the rule. The form of the runfiles import is `<workspace_name>/<package_dir>/<import>`.
            The actual `workspace_name` is not interesting such that the first part can be simply
            stripped. Next the package to the Label is stripped leaving a path that would have been
            supplied on an `imports` attribute to a Rule.
            """

            # Other code in this file appears to assume *NIX path component separators?

            provider_import_parts = provider_import.split("/")
            package_parts = ctx.label.package.split("/")

            if 0 == len(provider_import_parts):
                return None

            scratch_parts = provider_import_parts[1:]  # remove the workspace name or _main

            for p in package_parts:
                if len(scratch_parts) > 0 and scratch_parts[0] == p:
                    scratch_parts = scratch_parts[1:]
                else:
                    return None

            return "/".join(scratch_parts)

        def provider_imports_to_attr_imports():
            result = []

            for provider_import in target[PyInfo].imports.to_list():
                attr_import = provider_import_to_attr_import(provider_import)
                if attr_import:
                    result.append(attr_import)

            return result

        if target[PyInfo].imports:
            imports.extend(provider_imports_to_attr_imports())
        runfiles = target[DefaultInfo].default_runfiles
        if runfiles and runfiles.files:
            generated_sources.extend([f for f in runfiles.files.to_list()])

    return [intellij_provider.create(
        provider = intellij_provider.PythonInfo,
        value = intellij_common.struct(
            version = getattr(runtime, "python_version", None),
            main = artifact_location.from_file(getattr(ctx.rule.file, "main", None)),
            main_module = getattr(ctx.rule.attr, "main_module", None),
            interpreter = (artifact_location.from_file(getattr(runtime, "interpreter", None)) or
                           artifact_location.from_execpath(getattr(runtime, "interpreter_path", None))),
            imports = imports,
            generated_sources = [artifact_location.from_file(f) for f in generated_sources],
        ),
        outputs = {
            "bsp-sync-artifacts": depset(generated_sources),
            "bsp-build-artifacts": depset(generated_sources),
        },
    )]

intellij_python_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    fragments = ["py"],
    provides = [intellij_provider.PythonInfo],
    toolchains = [TOOLCHAIN_TYPE],
)
