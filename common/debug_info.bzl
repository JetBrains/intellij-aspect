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

load(":artifact_location.bzl", "artifact_location")
load(":common.bzl", "intellij_common")

# CompilationMode enum; must match CompilationMode in ide_info.proto
_COMPILATION_MODE_FASTBUILD = 0
_COMPILATION_MODE_DBG = 1
_COMPILATION_MODE_OPT = 2

_COMPILATION_MODE_MAPPING = {
    "fastbuild": _COMPILATION_MODE_FASTBUILD,
    "dbg": _COMPILATION_MODE_DBG,
    "opt": _COMPILATION_MODE_OPT,
}

def _compilation_mode(ctx):
    mode = _COMPILATION_MODE_MAPPING.get(ctx.var["COMPILATION_MODE"], _COMPILATION_MODE_FASTBUILD)
    return intellij_common.encode_enum(mode)

def _action_info(action, *, with_outputs = False):
    """Converts an action to an ActionInfo. Returns None if the command line is not available."""
    if action.argv == None:
        return None

    return intellij_common.struct(
        mnemonic = action.mnemonic,
        arguments = action.argv,
        outputs = artifact_location.from_files(action.outputs.to_list()) if with_outputs else None,
    )

intellij_debug_info = struct(
    compilation_mode = _compilation_mode,
    action_info = _action_info,
)
