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

load("@bazel_skylib//lib:paths.bzl", "paths")
load("//testing/rules:module_dep.bzl", "TestModuleDep")

_RegistryModule = provider(fields = ["name", "mapping"])

_REGISTRY_PREFIX = "custom-registry"

# Bazel resolves a relative "local_path" against "module_base_path" from
# bazel_registry.json, which in turn is relative to the registry root.
_SOURCE_JSON_TEMPLATE = """
{
    "type": "local_path",
    "path": "%s/local/source"
}
"""

# The registry is materialized by merging this archive over the BCR archive, so
# this file has to be unzipped last for "module_base_path" to survive.
_REGISTRY_JSON = """
{
    "mirrors": [],
    "module_base_path": "modules"
}
"""

_METADATA_JSON = """
{
    "homepage": "",
    "maintainers": [],
    "versions": ["local"],
    "yanked_versions": {}
}
"""

def _realpath(base_path, file):
    """Calculates the real path of the file inside the registry zip."""
    return paths.relativize(file.short_path.removesuffix(".fix"), base_path)

def _registry_module_impl(ctx):
    module_name = ctx.attr.module_name or ctx.label.name

    source_json = ctx.actions.declare_file(ctx.label.name + "_source.json")
    ctx.actions.write(source_json, _SOURCE_JSON_TEMPLATE % module_name)

    metadata_json = ctx.actions.declare_file(ctx.label.name + "_metadata.json")
    ctx.actions.write(metadata_json, _METADATA_JSON)

    module_base = "%s/modules/%s" % (_REGISTRY_PREFIX, module_name)
    source_base = "%s/%s" % (ctx.label.package, ctx.attr.strip_prefix or module_name)

    module_files = [file for file in ctx.files.srcs if _realpath(source_base, file) == "MODULE.bazel"]

    if len(module_files) != 1:
        fail("srcs must contain exactly one MODULE.bazel file, got %d" % len(module_files))

    mapping = [
        "%s/local/source/%s=%s" % (module_base, _realpath(source_base, file), file.path)
        for file in ctx.files.srcs
    ] + [
        "%s/local/MODULE.bazel=%s" % (module_base, module_files[0].path),
        "%s/local/source.json=%s" % (module_base, source_json.path),
        "%s/metadata.json=%s" % (module_base, metadata_json.path),
    ]

    return [
        DefaultInfo(files = depset(ctx.files.srcs + [source_json, metadata_json])),
        _RegistryModule(name = module_name, mapping = mapping),
        TestModuleDep(name = module_name, version = "local", config = ctx.attr.config, flags = []),
    ]

registry_module = rule(
    implementation = _registry_module_impl,
    provides = [_RegistryModule, TestModuleDep],
    attrs = {
        "srcs": attr.label_list(
            allow_files = True,
            mandatory = True,
        ),
        "module_name": attr.string(
            default = "",
            doc = "the name of the module inside the registry",
        ),
        "strip_prefix": attr.string(
            default = "",
            doc = "a directory prefix to strip from all files, defaults to the target's name",
        ),
        "config": attr.string(
            default = "",
            doc = "Optional extra MODULE.bazel content appended after the bazel_dep line (e.g., use_extension, register_toolchains).",
        ),
    },
)

def _registry_impl(ctx):
    registry_json = ctx.actions.declare_file(ctx.label.name + "_registry.json")
    ctx.actions.write(registry_json, _REGISTRY_JSON)

    modules = [dep[_RegistryModule] for dep in ctx.attr.modules]

    mapping = [
        entry
        for module in modules
        for entry in module.mapping
    ] + ["%s/bazel_registry.json=%s" % (_REGISTRY_PREFIX, registry_json.path)]

    files = [
        file
        for dep in ctx.attr.modules
        for file in dep[DefaultInfo].files.to_list()
    ] + [registry_json]

    archive = ctx.actions.declare_file(ctx.label.name + ".zip")
    ctx.actions.run(
        inputs = files,
        executable = ctx.executable._zipper,
        outputs = [archive],
        arguments = ["c", archive.path] + mapping,
    )

    return [DefaultInfo(files = depset([archive]))]

registry = rule(
    implementation = _registry_impl,
    attrs = {
        "modules": attr.label_list(
            mandatory = True,
            providers = [_RegistryModule],
        ),
        "_zipper": attr.label(
            cfg = "exec",
            default = Label("@bazel_tools//tools/zip:zipper"),
            executable = True,
        ),
    },
)
