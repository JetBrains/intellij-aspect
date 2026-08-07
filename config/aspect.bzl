load("//common:provider.bzl", "intellij_provider")

_FILE_TEMPLATE = """
load("//intellij:aspect.bzl", "intellij_aspect")

{configs}
"""

_CONFIG_TEMPLATE = """
config_{hash} = intellij_aspect(
    modules = [{modules}], 
    fragments = [{fragments}], 
    toolchains = [{toolchains}],
)
"""

def _join_set(set, seperator, transform = str):
    return seperator.join([transform(it) for it in set])

def _as_string(value):
    return "\"%s\"" % value

def _power_set(values):
    result = []

    for value in values:
        subsets = [[value]]

        # add the current element to all previously generated subsets
        for subset in result:
            subsets.append(subset + [value])

        result.extend(subsets)

    return result

def _generate_configuration(groups):
    modules = set()
    fragments = set()
    toolchains = set()

    for group in groups:
        modules.add(group.owner)

        for module in group.deps:
            fragments.update(module.fragments)
            toolchains.update(module.toolchains)

    return _CONFIG_TEMPLATE.format(
        hash = abs(hash(_join_set(modules, seperator = "___"))),
        modules = _join_set(modules, seperator = ", ", transform = _as_string),
        fragments = _join_set(fragments, seperator = ", ", transform = _as_string),
        toolchains = _join_set(toolchains, seperator = ", ", transform = _as_string),
    )

def _aspect_config_impl(ctx):
    modules = [it[intellij_provider.ModuleGroup] for it in ctx.attr.modules]
    configs = [_generate_configuration(it) for it in _power_set(modules)]

    file = ctx.actions.declare_file(ctx.label.name + ".bzl")
    ctx.actions.write(file, _FILE_TEMPLATE.format(configs = "\n".join(configs)))

    return [DefaultInfo(files = depset([file]))]

aspect_config = rule(
    implementation = _aspect_config_impl,
    attrs = {
        "modules": attr.label_list(
            providers = [intellij_provider.ModuleGroup],
            mandatory = True,
        ),
    },
)
