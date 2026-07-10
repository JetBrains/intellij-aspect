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
