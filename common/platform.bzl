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

load("//config:config.bzl", "config")

def _is_linux():
    return config.os.lower().startswith("linux")

def _is_mac_os():
    return config.os.lower().startswith("mac os")

def _is_windows():
    return config.os.lower().startswith("windows")

platform = struct(
    OS = config.os,
    is_linux = _is_linux,
    is_mac_os = _is_mac_os,
    is_windows = _is_windows,
)
