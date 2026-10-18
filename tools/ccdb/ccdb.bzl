load("//intellij:aspect.bzl", "intellij_configure_aspect")
load("//modules:cc_info.bzl", cc_info = "module")
load("//modules:cc_toolchain_info.bzl", cc_toolchain_info = "module")
load("//modules:compilecommands.bzl", "OUTPUT_GROUP", compilecommands_info = "module")

compilecommands_aspect = intellij_configure_aspect(
    modules = [cc_info, cc_toolchain_info, compilecommands_info],
    container = "//:module_container",
)

def _ccdb_impl(ctx):
    compdb_files = depset(transitive = [
        getattr(dep[OutputGroupInfo], OUTPUT_GROUP, depset())
        for dep in ctx.attr.deps
    ])

    output_file = ctx.actions.declare_file(ctx.label.name + ".output")

    args = ctx.actions.args()
    args.use_param_file("@%s", use_always = True)
    args.set_param_file_format("multiline")
    args.add_all(compdb_files)

    ctx.actions.run(
        inputs = compdb_files,
        outputs = [output_file],
        executable = ctx.executable._generator,
        arguments = [output_file.path, args],
        execution_requirements = {
            "no-sandbox": "1",
            "no-cache": "1",
        },
    )

    return [DefaultInfo(files = depset([output_file]))]

ccdb = rule(
    implementation = _ccdb_impl,
    attrs = {
        "deps": attr.label_list(
            mandatory = True,
            aspects = [compilecommands_aspect],
        ),
        "_generator": attr.label(
            default = Label("//tools/ccdb:generator"),
            cfg = "exec",
            executable = True,
        ),
    },
)
