load("//common:provider.bzl", "intellij_provider")

# Output groups used
_SYNC_OUTPUT = "intellij-sync"
_BUILD_OUTPUT = "intellij-build"

def _rule(name, implementation, field = None, setup = None, attrs = None, toolchains = None):
    """Declares a module rule."""

    def rule_implementation(ctx):
        return [intellij_provider.Module(
            name = name,
            field = field,
            impl = implementation,
            attr = {name: getattr(ctx.attr, name) for name in list(attrs or [])},
            deps = depset([it[intellij_provider.Module] for it in ctx.attr.deps]),
        )]

    return rule(
        implementation = rule_implementation,
        provides = [intellij_provider.Module],
        attrs = (attrs or {}) | {"deps": attr.label_list(providers = [intellij_provider.Module])},
        toolchains = toolchains or [],
    )

def _declare(name, rule, fragments = None, toolchains = None, **kwargs):
    """Declares a module definition."""

    visibility = kwargs.pop("visibility", ["//visibility:public"])
    tags = kwargs.pop("tags", []) + ["intellij-aspect-module"]

    rule(
        name = name,
        visibility = visibility,
        tags = tags,
        **kwargs
    )

def _result(value, *, internal_value = None, outputs = None, dependencies = None, toolchains = None):
    # TODO: this could be optimized by returning none here if nothing is provided

    return struct(
        present = True,
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
    result = _result,
)
