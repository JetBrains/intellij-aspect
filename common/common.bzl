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

load(":provider.bzl", "intellij_provider")
load(":version.bzl", "bazel_version")

# fallback configurations if short id is not available
_FALLBACK_CONFIG = "00000f1"
_FALLBACK_EXEC_CONFIG = "00000f2"

def _struct(**kwargs):
    """A replacement for standard `struct` function that omits the fields with None value."""

    # TODO: this could be further improved with just `if kwargs[name]` to filter all default values
    return struct(**{name: kwargs[name] for name in kwargs if kwargs[name] != None})

def _depset_or_none(direct = None, *, transitive = None):
    """Alternative depset constructor
    Returns None instead of a trivially empty set and forwards the value in case it should be constructed out of
    precisely one transitive member. Returning None instead of an empty depset allows ignoring those empty sets
    when accumulating at the next higher level.
    """
    if not direct:
        if not transitive:
            return None
        if len(transitive) == 1:
            return transitive[0]
    return depset(direct = direct, transitive = transitive)

def _struct_update(s, **kwargs):
    """Return new struct that has the same key-value pairs as the given one, expect where specifed via the keyword args."""
    attrs = dir(s)

    # two deprecated methods of struct
    if "to_json" in attrs:
        attrs.remove("to_json")
    if "to_proto" in attrs:
        attrs.remove("to_proto")
    data = {key: getattr(s, key) for key in attrs}
    for k, v in kwargs.items():
        data[k] = v
    return _struct(**data)

def _label_is_external(label):
    """Determines whether a label corresponds to an external artifact."""
    return label.workspace_root.startswith("external/")

def _label_to_string(label):
    """Stringifies a label, making sure any leading '@'s are stripped from main repo labels."""
    s = str(label)

    # If the label is in the main repo, make sure any leading '@'s are stripped so that tests are
    # okay with the fixture setups.
    return s.lstrip("@") if s.startswith("@@//") or s.startswith("@//") else s

def _attr_as_str(ctx, name, strict = False):
    """Returns the attr as a string. Or the empty string if the attr is invalid."""
    value = getattr(ctx.rule.attr, name, None)

    if not value or type(value) != "string":
        return None if strict else ""

    return value

def _attr_as_target(ctx, name):
    """Returns the attr as a target. Or the empty None if the attr is invalid."""
    value = getattr(ctx.rule.attr, name, None)

    if not value or type(value) != "Target":
        return None

    return value

def _attr_as_list(ctx, name, strict = False):
    """Returns the attr as a list. Or the empty list if the attr is invalid."""
    value = getattr(ctx.rule.attr, name, None)

    if not value:
        return []

    if type(value) != "list":
        return [] if strict else [value]

    return value

def _attr_as_label_list(ctx, name, strict = False):
    """Returns the attr as a list of targets. Filters out everything except targets."""
    return [it for it in _attr_as_list(ctx, name, strict) if type(it) == "Target"]

def _attr_as_info_list(ctx, name, strict = False):
    """Returns the attr as a list of IntelliJInfo. Filters out everything except targets."""

    # note: it is safe to assume that every target carries the IntelliJInfo provider since we walk aspect_attr = ["*"]
    return [it[intellij_provider.IntelliJInfo] for it in _attr_as_list(ctx, name, strict) if type(it) == "Target"]

def _attr_as_string_list(ctx, name, strict = False):
    """Returns the attr as a list of strings. Filters out everything except strings."""
    return [it for it in _attr_as_list(ctx, name, strict) if type(it) == "string"]

def _attr_as_dict(ctx, name):
    """Returns the attr as a dict. Or the empty dict if the attr is invalid."""
    value = getattr(ctx.rule.attr, name, None)

    if not value or type(value) != "dict":
        return {}

    return value

def _attr_as_string_dict(ctx, name):
    """Returns the attr as a dict of strings. Filters out everything except strings."""
    return {key: value for key, value in _attr_as_dict(ctx, name).items() if type(value) == "string"}

def _is_intellij_aspect_id(id):
    """Checks whether an aspect id refers to an aspect provided by us."""
    (_, name) = id.split("%")
    return name.removeprefix("_").startswith("intellij_")

def _target_config(ctx):
    """
    Returns the current configuration of the target. If the configuration id is
    not available it only differentiates between tool and default configuration.
    """
    configuration = getattr(ctx.configuration, "short_id", None)

    if not configuration and _is_exec_configuration(ctx):
        return _FALLBACK_EXEC_CONFIG

    return configuration or _FALLBACK_CONFIG

def _target_key(target, ctx, aspect_ids):
    """
    Creates a target key. Aspect ids cannot be taken from the ctx since the
    current context might not see all aspects.
    """
    return _struct(
        aspect_ids = [id for id in aspect_ids if not _is_intellij_aspect_id(id)],
        label = intellij_common.label_to_string(target.label),
        configuration = _target_config(ctx),
    )

def _aspect(**kwargs):
    """A replacement for the standard `aspect` function that modifies some of the arguments."""
    if bazel_version.le(8):
        kwargs.pop("toolchains_aspects", None)

    return aspect(**kwargs)

def _is_exec_configuration(ctx):
    """Simple heuristic to detect if a context is building for the exec configuration."""
    return "-exec" in ctx.genfiles_dir.path

def _target_keys_from(targets):
    """Extracts keys from given list of targets. Omits the targets without TargetInfo provider."""
    return [
        target[intellij_common.TargetInfo].partial_key
        for target in targets
        if intellij_common.TargetInfo in target
    ]

intellij_common = struct(
    struct = _struct,
    struct_update = _struct_update,
    depset = _depset_or_none,
    aspect = _aspect,
    label_is_external = _label_is_external,
    label_to_string = _label_to_string,
    attr_as_str = _attr_as_str,
    attr_as_target = _attr_as_target,
    attr_as_list = _attr_as_list,
    attr_as_label_list = _attr_as_label_list,
    attr_as_info_list = _attr_as_info_list,
    attr_as_string_list = _attr_as_string_list,
    attr_as_dict = _attr_as_dict,
    attr_as_string_dict = _attr_as_string_dict,
    is_exec_configuration = _is_exec_configuration,
    target_key = _target_key,
    target_keys_from = _target_keys_from,
)
