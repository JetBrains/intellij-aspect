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

load(":common.bzl", "intellij_common")

_INFO = "intellij-info"
_SYNC = "intellij-sync"
_BUILD = "intellij-build"

def _source_depset(direct = None):
    """Return a depset with direct files filtered to sources only"""
    return intellij_common.depset(
        [f for f in direct or [] if f.is_source],
    )

def _build_depset(direct = None, *, transitive = None):
    """Return a depset with direct files filtered to non-sources only"""
    return intellij_common.depset(
        [f for f in direct or [] if not f.is_source],
        transitive = transitive,
    )

def _from_files(files = None, *, build_transitive = None):
    return {
        _SYNC: _source_depset(files),
        _BUILD: _build_depset(files, transitive = build_transitive),
    }

intellij_output_groups = struct(
    INFO = _INFO,
    SYNC = _SYNC,
    BUILD = _BUILD,
    from_files = _from_files,
)
