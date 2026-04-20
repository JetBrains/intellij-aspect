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
load("@rules_java//java/common:java_semantics.bzl", "semantics")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:ide_info.bzl", "ide_info")
load(":provider.bzl", "intellij_provider")

JAVA_TOOLCHAIN_TYPE = semantics.JAVA_RUNTIME_TOOLCHAIN_TYPE

def _aspect_impl(target, ctx):
    if not java_common.JavaToolchainInfo in target:
        return [intellij_provider.JavaToolchainInfo(present = False)]

    toolchain = target[java_common.JavaToolchainInfo]
    runtime = toolchain.java_runtime
    boot_classpath_java_home = getattr(getattr(toolchain, "_bootclasspath_info", None), "_system_path", None)
    info = intellij_common.struct(
        source_version = toolchain.source_version,
        target_version = toolchain.target_version,
        java_home = artifact_location.from_execpath(runtime.java_home),
        boot_classpath_java_home = artifact_location.from_execpath(boot_classpath_java_home),
    )
    return [intellij_provider.create_toolchain(
        provider = intellij_provider.JavaToolchainInfo,
        info_file = ide_info.write_toolchain(target, ctx, "java_toolchain_info", info),
        owner = target,
    )]

intellij_java_toolchain_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    fragments = ["java"],
    provides = [intellij_provider.JavaToolchainInfo],
    toolchains_aspects = [str(JAVA_TOOLCHAIN_TYPE)],
)
