_IntelliJModule = provider(
    doc = "A language or toolchain module of the IntelliJ aspect.",
    fields = {
        "name": "str - Identity of the module. Used to look up its result from other modules.",
        "field": "str|None - Field of intellij-info.txt the value is written to. None for modules that only feed other modules.",
        "impl": "function - impl(target, ctx, data, results) -> struct|None, called for every visited target.",
        "attr": "dict - Collection of module attributes.",
    },
)

def _rule(name, implementation, field = None, setup = None, attrs = None, toolchains = None):
    """Declares a module rule."""

    def rule_implementation(ctx):
        return [_IntelliJModule(
            name = name,
            field = field,
            impl = implementation,
            attr = {name: getattr(ctx.attr, name) for name in list(attrs or [])},
        )]

    rule = rule(
        implementation = rule_implementation,
        provides = [_IntelliJModule],
        attrs = attrs or {},
        toolchains = toolchains or [],
    )

    return rule

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

    return struct(
        name = name,
        target = native.package_relative_label(name),
        fragments = fragments,
        toolchains = toolchains,
    )

def _result(value = None, internal_value = None, outputs = None, dependencies = None, toolchains = None):
    # TODO: this could be optimized by returning none here if nothing is provided

    return struct(
        present = True,
        value = value,
        internal_value = internal_value or struct(),
        outputs = outputs or {},
        dependencies = dependencies or {},
        toolchains = toolchains or [],
    )

intellij_module = struct(
    rule = _rule,
    declare = _declare,
    result = _result,
    Module = _IntelliJModule,
)
