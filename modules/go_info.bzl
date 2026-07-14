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

load("@rules_go//go:def.bzl", "go_context")
load("@rules_go//go/private:common.bzl", "GO_TOOLCHAIN_LABEL")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:dependencies.bzl", "intellij_deps")
load(":provider.bzl", "intellij_provider")

# As go targets do not reliably have a provider, we need to detect go targets by rule_kind as well
_GO_RULE_KINDS = [
    "gazelle_binary",
    "go_binary",
    "go_library",
    "go_test",
    "go_source",
    "go_appengine_binary",
    "go_appengine_library",
    "go_appengine_test",
    "go_proto_library",
    "go_wrap_cc",
    "go_web_test",
]

COMPILE_TIME_DEPS = ["embed"]

def _go_sdk(ctx):
    go = go_context(ctx)
    if go == None:
        return None
    return artifact_location.from_file(go.sdk.go)

def _sources(target, ctx):
    if ctx.rule.kind in [
        "go_binary",
        "go_library",
        "go_test",
        "go_source",
        "go_appengine_binary",
        "go_appengine_library",
        "go_appengine_test",
    ]:
        sources = [f for src in getattr(ctx.rule.attr, "srcs", []) for f in src.files.to_list()]
    elif ctx.rule.kind == "go_wrap_cc":
        sources = [f for f in target.files.to_list() if f.basename.endswith(".go")]
    elif OutputGroupInfo in target and hasattr(target[OutputGroupInfo], "go_generated_srcs"):
        sources = [f for f in target[OutputGroupInfo].go_generated_srcs.to_list() if f.basename.endswith(".go")]
    else:
        sources = []
    return sources

def _import_path(ctx):
    import_path = getattr(ctx.rule.attr, "importpath", None)
    if import_path:
        return import_path
    prefix = None
    if hasattr(ctx.rule.attr, "_go_prefix"):
        prefix = ctx.rule.attr._go_prefix.go_prefix
    if not prefix:
        return None
    import_path = prefix
    if ctx.label.package:
        import_path += "/" + ctx.label.package
    if ctx.label.name != "go_default_library":
        import_path += "/" + ctx.label.name
    return import_path

def _embed(ctx):
    if not getattr(ctx.rule.attr, "embed", None):
        return []
    return intellij_common.target_keys_from(ctx.rule.attr.embed)

def _aspect_impl(target, ctx):
    # Ideally, we would like to check for the presence of a provider to be sure, the target is defined by
    # the expected rule set; however, the currently-used provider was only introduced in 2024 and older versions
    # of rules_go are still in use. Therefore, check against a list of rule kinds.
    if ctx.rule.kind not in _GO_RULE_KINDS and \
       (OutputGroupInfo not in target or not hasattr(target[OutputGroupInfo], "go_generated_srcs")):  # support at least a subset of custom rules not hardcoded in _GO_RULE_KINDS
        return [intellij_provider.GoInfo(present = False)]

    sources = _sources(target, ctx)
    return [intellij_provider.create(
        ctx = ctx,
        provider = intellij_provider.GoInfo,
        value = intellij_common.struct(
            import_path = _import_path(ctx),
            sdk_home_path = _go_sdk(ctx),
            sources = [artifact_location.from_file(f) for f in sources],
            embed = _embed(ctx),
        ),
        dependencies = {
            intellij_deps.COMPILE_TIME: intellij_deps.collect(
                ctx,
                attributes = COMPILE_TIME_DEPS,
            ),
        },
        outputs = {
            intellij_provider.SYNC_OUTPUT: depset(sources),
        },
    )]

intellij_go_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.GoInfo],
    toolchains = [
        config_common.toolchain_type(str(GO_TOOLCHAIN_LABEL), mandatory = False),
    ],
)
