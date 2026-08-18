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

load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

def _implementation(target, ctx, attr):
    # targets built under exec configuration are most likely used as local tool
    if intellij_common.is_exec_configuration(ctx):
        return None

    files_to_run = target[DefaultInfo].files_to_run

    # files_to_run.executable is None for non-executable targets
    if not files_to_run or getattr(files_to_run, "executable", None) == None:
        return None

    return intellij_module.result(
        intellij_common.struct(
            executable_file = artifact_location.from_file(files_to_run.executable),
            runfiles_manifest = artifact_location.from_file(getattr(files_to_run, "runfiles_manifest", None)),
        ),
    )

_aspect = intellij_module.aspect(
    provider = intellij_provider.RunInfo,
    implementation = _implementation,
    field = "executable_info",
)

module = intellij_module.define(_aspect)
