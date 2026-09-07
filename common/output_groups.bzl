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
    # Files that have to be built for a sync without a full build. Plain source files (including
    # prebuilt jars and headers in external repositories) never need building and are listed in
    # the intellij-info.txt files instead, so they are deliberately not part of this group: every
    # artifact in a requested output group is retained per top-level target by Bazel.
    SYNC = "intellij-sync",
    BUILD = "intellij-build",
)
