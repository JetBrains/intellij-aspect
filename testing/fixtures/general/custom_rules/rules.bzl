def _executable_impl(ctx):
    script = ctx.actions.declare_file(ctx.label.name + ".sh")
    ctx.actions.write(
        output = script,
        content = "#!/usr/bin/env bash\nexit 0\n",
        is_executable = True,
    )
    return [DefaultInfo(files = depset([script]), executable = script)]

custom_binary = rule(
    implementation = _executable_impl,
    executable = True,
)

custom_test = rule(
    implementation = _executable_impl,
    test = True,
)

def _library_impl(ctx):
    out = ctx.actions.declare_file(ctx.label.name + ".txt")
    ctx.actions.write(output = out, content = "")
    return [DefaultInfo(files = depset([out]))]

custom_library = rule(
    implementation = _library_impl,
)

# Hostile attribute types: attributes the aspect reads (see common/ide_info.bzl)
# declared with deliberately wrong types, plus attributes the aspect never reads.
_HOSTILE_ATTRS = {
    "srcs": attr.bool(default = True),  # aspect expects a label_list
    "env": attr.string(default = "not-a-dict"),  # aspect expects a string_dict
    "env_inherit": attr.string(default = "not-a-list"),  # aspect expects a string_list
    "data": attr.string(default = "not-a-label"),  # aspect expects a label_list
    "deps": attr.string(default = "not-a-label"),  # aspect expects a label_list
    "runtime_deps": attr.string(default = "not-a-label"),  # aspect expects a label_list
    "weird_flag": attr.bool(default = True),  # attribute the aspect never reads
    "mode": attr.string(default = "chaos"),  # attribute the aspect never reads
}

weird_binary = rule(
    implementation = _executable_impl,
    executable = True,
    attrs = _HOSTILE_ATTRS,
)

weird_test = rule(
    implementation = _executable_impl,
    test = True,
    attrs = {
        "srcs": attr.bool(default = True),
        "data": attr.string(default = "not-a-label"),
        "deps": attr.string(default = "not-a-label"),
        "runtime_deps": attr.string(default = "not-a-label"),
        "weird_flag": attr.bool(default = True),
        "mode": attr.string(default = "chaos"),
    },
)

weird_library = rule(
    implementation = _library_impl,
    attrs = _HOSTILE_ATTRS,
)

makevar_binary = rule(
    implementation = _executable_impl,
    executable = True,
    attrs = {
        "env": attr.string_dict(),
        "data": attr.label_list(allow_files = True),
    },
)
