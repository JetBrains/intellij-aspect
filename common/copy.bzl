# Copyright 2019 The Bazel Authors. All rights reserved.
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

# Adapted from Skylib
# https://github.com/bazelbuild/bazel-skylib/blob/390ecf872568a9fc1752cbade56be52cd4263758/rules/private/copy_file_private.bzl#L24

load("//config:config.bzl", "config")

def copy(ctx, src, dst, progress, mnemonic):
    if config.os.startswith("windows"):
        bat = ctx.actions.declare_file(dst.basename.replace(".", "_") + "-cmds.bat")
        ctx.actions.write(
            output = bat,
            content = "@copy /Y \"%s\" \"%s\" >NUL" % (
                src.path.replace("/", "\\"),
                dst.path.replace("/", "\\"),
            ),
            is_executable = True,
        )
        ctx.actions.run(
            inputs = [src, bat],
            outputs = [dst],
            executable = "cmd.exe",
            arguments = ["/C", bat.path.replace("/", "\\")],
            mnemonic = mnemonic,
            progress_message = progress,
            use_default_shell_env = True,
        )
    else:
        ctx.actions.run(
            inputs = [src],
            outputs = [dst],
            mnemonic = mnemonic,
            progress_message = progress,
            executable = "sh",
            arguments = ["-c", 'cp "$1" "$2"', "--", src.path, dst.path],
            use_default_shell_env = True,
        )
