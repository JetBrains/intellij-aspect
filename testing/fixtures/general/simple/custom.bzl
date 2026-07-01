def _rule_impl(ctx):
    main = ctx.actions.declare_file("main_" + ctx.label.name)
    ctx.actions.write(main, "Main for " + ctx.label.name)
    files = [main]
    if ctx.attr.srcs:
        src = ctx.actions.declare_file("src_" + ctx.label.name)
        ctx.actions.write(src, "Sources for " + ctx.label.name)
        files += [src]
    return [DefaultInfo(files = depset(files))]

custom_rule = rule(
    implementation = _rule_impl,
    attrs = {"srcs": attr.bool()},
)

def custom_macro(name, srcs):
    for ext in ["A", "B", "C"]:
        custom_rule(
            name = name + "_" + ext,
            srcs = srcs,
        )
