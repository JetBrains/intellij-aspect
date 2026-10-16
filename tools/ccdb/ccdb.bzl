load("//intellij:aspect.bzl", "intellij_configure_aspect")
load("//modules:cc_info.bzl", cc_info = "module")
load("//modules:cc_toolchain_info.bzl", cc_toolchain_info = "module")
load("//modules:compilecommands.bzl", compilecommands_info = "module")

compilecommands_aspect = intellij_configure_aspect(
    modules = [cc_info, cc_toolchain_info, compilecommands_info],
    container = "//:module_container",
)

def _ccdb_impl(ctx):
    packages = []

    outputs = []  # TODO: calculate .clangd files from packages in the files

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
            aspects = [compilecommands_aspect],
        ),
        # "_tool": attr.label(
        #     default = Label("..."),
        #     executable = True,
        # ),
    },
)
