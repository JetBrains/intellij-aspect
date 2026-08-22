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

load("@rules_java//java:defs.bzl", "java_common")
load("@rules_java//java/common:java_semantics.bzl", JAVA_SEMANTICS = "semantics")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

JAVA_TOOLCHAIN_TYPE = JAVA_SEMANTICS.JAVA_TOOLCHAIN_TYPE

def _implementation(target, ctx, attrs):
    if not java_common.JavaToolchainInfo in target:
        return None

    toolchain = target[java_common.JavaToolchainInfo]
    runtime = toolchain.java_runtime
    boot_classpath_java_home = getattr(getattr(toolchain, "_bootclasspath_info", None), "_system_path", None)
    info = intellij_common.struct(
        source_version = toolchain.source_version,
        target_version = toolchain.target_version,
        java_home = artifact_location.from_execpath(runtime.java_home),
        boot_classpath_java_home = artifact_location.from_execpath(boot_classpath_java_home) if boot_classpath_java_home else None,
        is_exec_config = intellij_common.is_exec_configuration(ctx),
    )

    return intellij_module.result(info)

_aspect = intellij_module.aspect(
    provider = intellij_provider.JavaToolchainInfo,
    implementation = _implementation,
    field = "java_toolchain_info",
)

module = intellij_module.define(
    file = "java_toolchain_info",
    aspect = _aspect,
    toolchains = [JAVA_TOOLCHAIN_TYPE],
    fragments = ["java"],
    rulesets = ["JAVA"],
)
