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

# Manually maintained list of latest supported Bazel versions per major release.
_VERSIONS = {
    7: "7.7.0",
    8: "8.6.0",
    9: "9.0.0",
}

def _ge(v, t):
    return v >= t

def _gt(v, t):
    return v > t

def _le(v, t):
    return v <= t

def _lt(v, t):
    return v < t

_SPEC_OPS = {
    ">=": _ge,
    ">": _gt,
    "<=": _le,
    "<": _lt,
}

def resolve(spec):
    """Parses a Bazel version spec into a list of version strings.

    Supports:
        None        - all registered versions
        int         - exact major version (e.g., 8)
        str ">=8"   - all versions with major >= 8
        str ">7"    - all versions with major > 7
        str "<=8"   - all versions with major <= 8
        str "<9"    - all versions with major < 9
        str "8"     - exact major version as string
        list        - pass-through (already a version string list)
    """
    if type(spec) == "list":
        return spec

    if spec == None:
        return [_VERSIONS[m] for m in sorted(_VERSIONS.keys())]

    if type(spec) == "int":
        return [_VERSIONS[spec]]

    for op, fn in _SPEC_OPS.items():
        if spec.startswith(op):
            t = int(spec[len(op):])
            return [_VERSIONS[m] for m in sorted(_VERSIONS.keys()) if fn(m, t)]

    return [_VERSIONS[int(spec)]]
