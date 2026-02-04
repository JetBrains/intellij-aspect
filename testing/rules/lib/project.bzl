load("@bazel_skylib//lib:paths.bzl", "paths")

def _realpath(base_path, file):
    """Calculates the real path of the file inside the project zip."""
    return paths.relativize(file.short_path.removesuffix(".fix"), base_path)

def _test_project_impl(ctx):
    base = "%s/%s" % (ctx.label.package, ctx.attr.strip_prefix or ctx.label.name)

    mapping = [
        "%s=%s" % (_realpath(base, file), file.path)
        for file in ctx.files.srcs
    ]

    archive = ctx.actions.declare_file(ctx.label.name + ".zip")
    ctx.actions.run(
        inputs = ctx.files.srcs,
        executable = ctx.executable._zipper,
        outputs = [archive],
        arguments = ["c", archive.path] + mapping,
    )

    return [DefaultInfo(files = depset([archive]))]

project_archive = rule(
    attrs = {
        "srcs": attr.label_list(
            allow_files = True,
            mandatory = True,
        ),
        "strip_prefix": attr.string(
            default = "",
            doc = "a directory prefix to strip from all files, defaults to the target's name",
        ),
        "_zipper": attr.label(
            cfg = "exec",
            default = Label("@bazel_tools//tools/zip:zipper"),
            executable = True,
        ),
    },
    implementation = _test_project_impl,
)
