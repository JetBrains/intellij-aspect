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

load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:make_variables.bzl", "expand_make_variables")
load(":provider.bzl", "intellij_provider")

def _get_reosource_strip_prefix(ctx):
    prefix = getattr(ctx.rule.attr, "resource_strip_prefix", None)
    if type(prefix) == "string":
        return prefix
    if type(prefix) == "Target":
        # special case for rules_kotlin
        return prefix.label.name

def _get_jvm_info(target, ctx):
    resources_attr = getattr(ctx.rule.attr, "resources", None)
    if resources_attr == None and ctx.rule.kind.endswith("_resources"):
        # https://github.com/JetBrains/intellij-community/blob/b41a4084da5521effedd334e28896fd9d07410da/build/jvm-rules/rules/resource.bzl#L49
        resources_attr = getattr(ctx.rule.attr, "files", None)

    resources = []
    if type(resources_attr) == "list":
        resources = artifact_location.from_list(resources_attr)

    return intellij_common.struct(
        args = intellij_common.attr_as_list(ctx, "args"),
        main_class = getattr(ctx.rule.attr, "main_class", None),
        jvm_flags = expand_make_variables(ctx, True, intellij_common.attr_as_list(ctx, "jvm_flags")),
        resource_strip_prefix = _get_reosource_strip_prefix(ctx),
        resources = resources,
    )

def _aspect_impl(target, ctx):
    if not any([intellij_provider.get(target, it) for it in intellij_provider.JVM_MODULES]):
        return [intellij_provider.JvmInfo(present = False)]

    return [intellij_provider.create(
        provider = intellij_provider.JvmInfo,
        value = _get_jvm_info(target, ctx),
    )]

intellij_jvm_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.JvmInfo],
    required_aspect_providers = [[it] for it in intellij_provider.JVM_MODULES],
)
