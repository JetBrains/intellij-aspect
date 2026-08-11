load("//common:provider.bzl", "intellij_provider")

# Output groups used
_SYNC_OUTPUT = "intellij-sync"
_BUILD_OUTPUT = "intellij-build"

def _rule(name, implementation, field = None, setup = None, attrs = None, fragments = None, toolchains = None):
    """Declares a module rule."""

    def rule_implementation(ctx):
        module = intellij_provider.Module(
            name = name,
            field = field,
            impl = implementation,
            attr = {name: getattr(ctx.attr, name) for name in list(attrs or [])},
            fragments = fragments or [],
            toolchains = toolchains or [],
        )

        return [module, intellij_provider.ModuleGroup(deps = [module], owner = ctx.label)]

    return rule(
        implementation = rule_implementation,
        provides = [intellij_provider.Module, intellij_provider.ModuleGroup],
        attrs = (attrs or {}) | {"deps": attr.label_list(providers = [intellij_provider.ModuleGroup])},
        toolchains = toolchains or [],
    )

def _filegroup():
    native.filegroup(
        name = "files",
        srcs = ["BUILD"] + native.glob(["**/*.bzl"]),
        visibility = ["//visibility:public"],
    )

def _declare(name, rule, **kwargs):
    """Declares a module definition."""

    tags = kwargs.pop("tags", []) + ["intellij-aspect-module"]
    rule(name = name, tags = tags, **kwargs)

def _module_group_impl(ctx):
    return [intellij_provider.ModuleGroup(
        deps = [dep[intellij_provider.Module] for dep in ctx.attr.deps],
        owner = ctx.label,
    )]

_module_group = rule(
    implementation = _module_group_impl,
    provides = [intellij_provider.ModuleGroup],
    attrs = {"deps": attr.label_list(providers = [intellij_provider.Module])},
)

def _group(**kwargs):
    """docs."""

    visibility = kwargs.pop("visibility", ["//visibility:public"])
    _module_group(visibility = visibility, **kwargs)

def _result(value, *, internal_value = None, outputs = None, dependencies = None, toolchains = None):
    # TODO: this could be optimized by returning none here if nothing is provided

    return struct(
        value = value,
        internal_value = internal_value or struct(),
        outputs = outputs or {},
        dependencies = dependencies or {},
        toolchains = toolchains or [],
    )

def _lookup(target, name):
    if intellij_provider.Info not in target:
        return None

    for (module, value) in target[intellij_provider.Info].modules.items():
        if module.name == name:
            return value

    return None

intellij_module = struct(
    SYNC_OUTPUT = _SYNC_OUTPUT,
    BUILD_OUTPUT = _BUILD_OUTPUT,
    rule = _rule,
    declare = _declare,
    group = _group,
    result = _result,
    lookup = _lookup,
    filegroup = _filegroup,
)
