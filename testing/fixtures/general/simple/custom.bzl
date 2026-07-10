def _rule_impl(ctx):
    main = ctx.actions.declare_file("main_" + ctx.label.name)
    ctx.actions.write(main, "#!/bin/sh\necho Main for '%s'" % (ctx.label.name,), is_executable = True)
    files = [main]
    if ctx.attr.srcs:
        src = ctx.actions.declare_file("src_" + ctx.label.name)
        ctx.actions.write(src, "Sources for " + ctx.label.name)
        files += [src]
    return [DefaultInfo(files = depset(files), executable = main)]

custom_rule = rule(
    implementation = _rule_impl,
    attrs = {"srcs": attr.bool()},
    executable = True,
)

def custom_macro(name, srcs):
    for ext in ["A", "B", "C"]:
        custom_rule(
            name = name + "_" + ext,
            srcs = srcs,
        )
