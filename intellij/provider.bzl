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

load("//common:common.bzl", "intellij_common")
load("//common:output_groups.bzl", "intellij_output_groups")
load("//common:provider.bzl", "intellij_provider")

def _create():
    """Creates a new builder. Optimisation for creating more efficient depsets."""
    return struct(outputs = {}, dependencies = {}, aspect_ids = {})

def _append_depset(dst, src):
    """Appends every depset from the source dict[depset] to the destination dict[list[depset]]."""
    for key in list(src):
        if src[key]:
            if key in dst:
                dst[key].append(src[key])
            else:
                dst[key] = [src[key]]

def _append(builder, src):
    """Appends all data from the source to the builder. Source must be either an IntellijInfo provider or a module result struct."""
    _append_depset(builder.outputs, src.outputs)
    _append_depset(builder.dependencies, src.dependencies)

    # only the module provider exposes the aspect ids
    builder.aspect_ids.update({id: True for id in getattr(src, "aspect_ids", [])})

def _append_outputs(builder, src):
    """Appends only the outputs of the sources to the builder. Source must be either an IntellijInfo provider or a module result struct."""
    _append_depset(builder.outputs, src.outputs)

def _append_ide_infos(builder, files):
    """Appends a list intellij ide info files."""
    if not files:
        return

    _append_depset(builder.outputs, {intellij_output_groups.INFO: depset(files)})

def _append_dependencies(builder, group, deps):
    """Appends all dependencies to the specified dependency group."""
    if deps == None:
        return
    _append_depset(builder.dependencies, {group: deps})

def _append_output(builder, group, files):
    """Appends the given files to the specified output group."""
    if files:
        _append_depset(builder.outputs, {group: depset(files)})

def _build_depset(src):
    """Builds one dict[depset] from the source dict[list[depset]]."""
    return {
        key: intellij_common.depset(transitive = value)
        for key, value in src.items()
        if value
    }

def _build_target_key(builder, target, ctx):
    """Creates a target key. Aspect ids cannot only be taken from the ctx since the current context might not see all aspects."""
    aspect_ids = {id: True for id in ctx.aspect_ids} | builder.aspect_ids
    return intellij_common.target_key(target, ctx, aspect_ids.keys())

def _build(builder, target, ctx, results):
    """Builds a new IntelliJInfo provider."""
    return intellij_provider.IntelliJInfo(
        key = _build_target_key(builder, target, ctx),
        outputs = _build_depset(builder.outputs),
        dependencies = _build_depset(builder.dependencies),
        results = results,
    )

intellij_info_builder = struct(
    create = _create,
    append = _append,
    append_outputs = _append_outputs,
    append_output = _append_output,
    append_ide_infos = _append_ide_infos,
    append_dependencies = _append_dependencies,
    build_target_key = _build_target_key,
    build = _build,
)
