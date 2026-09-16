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

intellij_output_groups = struct(
    INFO = "intellij-info",
    SYNC = "intellij-sync",
    BUILD = "intellij-build",
    # The intellij-info.txt files written for the target itself; for targets without an info file
    # of their own (e.g. aliases or filegroups) the nearest info files of their dependencies.
    DIRECT_INFO = "intellij-info-direct",
    # Hidden alias of INFO: built, but not reported as top-level artifacts in the build event stream.
    HIDDEN_INFO = "_intellij-info",
)
