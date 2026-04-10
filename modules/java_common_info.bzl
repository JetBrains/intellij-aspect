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
load("//common:make_variables.bzl", "expand_make_variables")
load(":provider.bzl", "intellij_provider")

_LIST_FIELDS = [
    "javac_opts",
    "jars",
    "generated_jars",
    "jdeps",
]
_BOOL_FIELDS = [
    "jvm_target",
]

def _aspect_impl(target, ctx):
    if not any([intellij_provider.get(target, it) for it in intellij_provider.JVM_MODULES]):
        return [intellij_provider.JavaCommonInfo(present = False)]

    value = {}

    for it in intellij_provider.JVM_MODULES:
        contribution = getattr(intellij_provider.get(target, it).internal_value, "common", None)
        if not contribution:
            continue
        for k in _LIST_FIELDS:
            value[k] = value.get(k, []) + getattr(contribution, k, [])
        for k in _BOOL_FIELDS:
            value[k] = value.get(k, False) or getattr(contribution, k, False)

    return [intellij_provider.create(
        provider = intellij_provider.JavaCommonInfo,
        value = intellij_common.struct(**value),
    )]

intellij_java_common_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.JavaCommonInfo],
    required_aspect_providers = [[it] for it in intellij_provider.JVM_MODULES],
)
