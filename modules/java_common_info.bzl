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
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

_LIST_FIELDS = [
    "javac_opts",
    "jars",
    "generated_jars",
    "jdeps",
]

_BOOL_FIELDS = [
    "jvm_target",
]

def _implementation(target, ctx, attr):
    if not any([intellij_module.lookup(attr, it) for it in intellij_provider.JVM]):
        return None

    value = {}

    for it in intellij_provider.JVM:
        contributor = intellij_module.lookup(attr, it)
        if not contributor:
            continue
        contribution = getattr(contributor.internal_value, "java_common", struct())
        for k in _LIST_FIELDS:
            value[k] = value.get(k, []) + getattr(contribution, k, [])
        for k in _BOOL_FIELDS:
            value[k] = value.get(k, False) or getattr(contribution, k, False)

    return intellij_module.result(intellij_common.struct(**value))

_aspect = intellij_module.aspect(
    provider = intellij_provider.JavaCommonInfo,
    implementation = _implementation,
    field = "java_common",
)

module = intellij_module.define(
    file = "java_common_info",
    aspect = _aspect,
    rulesets = ["@rules_java", "@rules_kotlin", "@rules_scala"],
)
