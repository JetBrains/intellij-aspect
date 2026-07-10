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
load(":provider.bzl", "intellij_provider")

def _aspect_impl(target, ctx):
    files_to_run = target[DefaultInfo].files_to_run

    # files_to_run.executable is None for non-executable targets
    if not files_to_run or getattr(files_to_run, "executable", None) == None:
        return [intellij_provider.RunInfo(present = False)]

    return [intellij_provider.create(
        ctx = ctx,
        provider = intellij_provider.RunInfo,
        value = intellij_common.struct(
            executable_file = artifact_location.from_file(files_to_run.executable),
            runfiles_manifest = artifact_location.from_file(getattr(files_to_run, "runfiles_manifest", None)),
        ),
    )]

intellij_run_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.RunInfo],
)
