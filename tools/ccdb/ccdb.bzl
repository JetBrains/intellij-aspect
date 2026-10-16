def _ccdb_impl(ctx):
    packages = []

    outputs = [] # TODO: calculate .clangd files from packages in the files

    ctx.actions.run(
        inputs = ctx.files.deps,
        outputs = packages,
        tools = [ctx.executable._tool],
        arguments = [dep.path for dep in ctx.files.deps],
    )

    return [DefaultInfo(files = depset(outputs))]

ccdb = rule(
    implementation = _ccdb_impl,
    attrs = {
        "deps": attr.label_list(
            mandatory = True,
            aspect = xxx,
        ),
        "_tool": attr.label(
            default = Label("..."),
            executable = True,
        )
    }
)
