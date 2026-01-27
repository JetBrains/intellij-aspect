def _local_registry(ctx):
    out = ctx.actions.declare_file("%s.tar" % (ctx.label.name,))
    ctx.actions.run(
        outputs = [out],
        inputs = [ctx.file.archive, ctx.file.module_file],
        executable = ctx.executable._local_registry,
        arguments = [ctx.file.archive.path, ctx.file.module_file.path, out.path, ctx.attr.module_version, ctx.attr.module_name],
    )
    return [DefaultInfo(files = depset([out]))]

local_registry = rule(
    implementation = _local_registry,
    attrs = {
        "archive": attr.label(mandatory = True, allow_single_file = True),
        "module_file": attr.label(mandatory = True, allow_single_file = True),
        "module_name": attr.string(mandatory = True),
        "module_version": attr.string(mandatory = True),
        "_local_registry": attr.label(default = "//private/lib:local_registry", executable = True, cfg = "exec"),
    },
)
