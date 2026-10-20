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

def _declare_unique_file(ctx, *, name, hash_hint = None):
    parts = [name]
    parts.append(hash_hint or "")

    # The tags implicitly enter the action as "execution info" which is also part of the action equality. Therefore, we
    # have to include them in the hash. Hashing every tag seperatly avoid collsions of tags contianing ".".
    parts.extend([str(hash(tag)) for tag in ctx.rule.attr.tags])

    name = "%s.intellij-generated-%s" % (name, str(abs(hash(".".join(parts)))))

    return ctx.actions.declare_file(name)

def _copy(ctx, *, src, dst):
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
            mnemonic = "IntelliJCopy",
            progress_message = "Copying file from %s to %s" % (src.basename, dst.basename),
            use_default_shell_env = True,
        )
    else:
        ctx.actions.run(
            inputs = [src],
            outputs = [dst],
            mnemonic = "IntelliJCopy",
            progress_message = "Copying file from %s to %s" % (src.basename, dst.basename),
            executable = "sh",
            arguments = ["-c", 'cp "$1" "$2"', "--", src.path, dst.path],
            use_default_shell_env = True,
        )

def _expand_env(ctx, *, dst, resolve, env):
    """Writes a `<name>=<value>` line for every variable in resolve that is set during action execution."""

    # environment expansion not support on windows (also not required atm)
    if config.os.startswith("windows"):
        ctx.actions.write(dst, "")
        return

    script = ["set -eu", "{"]

    for name in resolve:
        # only write the variable if it is set, strip line breaks, otherwise the value could corrupt the file
        script.append("""if [ -n "${%s+x}" ]; then printf '%s=%%s\n' "${%s//[$'\r\n']/}"; fi""" % (name, name, name))

    script.append('} > "$1"')

    ctx.actions.run_shell(
        inputs = [],
        outputs = [dst],
        arguments = [dst.path],
        command = "\n".join(script),
        env = env,
        mnemonic = "IntelliJExpandEnv",
        progress_message = "Expanding environment of %{output}",
        execution_requirements = {
            "no-cache": "",
            "no-remote": "",
        },
    )

intellij_actions = struct(
    declare_unique_file = _declare_unique_file,
    copy = _copy,
    expand_env = _expand_env,
)
