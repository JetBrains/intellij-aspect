# Copyright 2025 JetBrains s.r.o. and contributors.
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

load(":version.bzl", "bazel_version")

_IntelliJTargetInfo = provider(
    doc = "Internal target identity used by IntelliJ aspects.",
    fields = {
        "owner": "Target - Underlying Bazel target that owns this info.",
        "key": "struct - Stable identity (label, configuration, aspect ids).",
    },
)

def _struct(**kwargs):
    """A replacement for standard `struct` function that omits the fields with None value."""

    # TODO: this could be further improved with just `if kwargs[name]` to filter all default values
    return struct(**{name: kwargs[name] for name in kwargs if kwargs[name] != None})

def _label_is_external(label):
    """Determines whether a label corresponds to an external artifact."""
    return label.workspace_root.startswith("external/")

def _label_to_string(label):
    """Stringifies a label, making sure any leading '@'s are stripped from main repo labels."""
    s = str(label)

    # If the label is in the main repo, make sure any leading '@'s are stripped so that tests are
    # okay with the fixture setups.
    return s.lstrip("@") if s.startswith("@@//") or s.startswith("@//") else s

def _attr_as_str(ctx, name):
    """Returns the attr as a string. Or the empty string if the attr is invalid."""
    value = getattr(ctx.rule.attr, name, None)

    if not value or type(value) != "string":
        return ""

    return value

def _attr_as_target(ctx, name):
    """Returns the attr as a target. Or the empty None if the attr is invalid."""
    value = getattr(ctx.rule.attr, name, None)

    if not value or type(value) != "Target":
        return None

    return value

def _attr_as_list(ctx, name):
    """Returns the attr as a list. Or the empty list if the attr is invalid."""
    value = getattr(ctx.rule.attr, name, None)

    if not value:
        return []

    if type(value) != "list":
        return [value]

    return value

def _attr_as_label_list(ctx, name):
    """Returns the attr as a list of targets. Filters out everything except targets."""
    return [it for it in _attr_as_list(ctx, name) if type(it) == "Target"]

def _intellij_info_aspect_impl(target, ctx):
    """Implementation for the target info aspect. Creates the key for the target."""
    key = _struct(
        aspect_ids = [it for it in ctx.aspect_ids if not "_intellij_target_info_aspect" in it],
        label = intellij_common.label_to_string(target.label),
        configuration = getattr(ctx.configuration, "short_id", None),
    )

    return [intellij_common.TargetInfo(key = key, owner = target)]

# This is the first aspect run and any other aspect depends on it. Provides a key
# to uniquely reference targets between aspects.
_intellij_target_info_aspect = aspect(
    implementation = _intellij_info_aspect_impl,
    attr_aspects = ["*"],
    provides = [_IntelliJTargetInfo],
)

def _aspect(**kwargs):
    """A replacement for the standard `aspect` function that modifies some of the arguments."""
    requires = kwargs.pop("requires", [])
    requires.append(_intellij_target_info_aspect)

    if bazel_version.le(8):
        kwargs.pop("toolchains_aspects", None)

    return aspect(
        attr_aspects = ["*"],
        requires = requires,
        **kwargs
    )

def _is_exec_configuration(ctx):
    """Simple heuristic to detect if a context is building for the exec configuration."""
    return "-exec" in ctx.genfiles_dir.path

intellij_common = struct(
    TargetInfo = _IntelliJTargetInfo,
    struct = _struct,
    aspect = _aspect,
    label_is_external = _label_is_external,
    label_to_string = _label_to_string,
    attr_as_str = _attr_as_str,
    attr_as_target = _attr_as_target,
    attr_as_list = _attr_as_list,
    attr_as_label_list = _attr_as_label_list,
    is_exec_configuration = _is_exec_configuration,
)
