load("@rules_pkg//pkg:providers.bzl", "PackageFilesInfo")

def _aspect_lib_impl(ctx):
    map = {
        "%s/%s" % (ctx.label.package, file.basename): file
        for dep in ctx.attr.files
        for file in dep[DefaultInfo].files.to_list()
    }

    # generate an empty build file
    build_file = ctx.actions.declare_file("BUILD.bazel")
    ctx.actions.write(build_file, "# generated BUILD file")

    map["%s/BUILD" % ctx.label.package] = build_file

    return [
        DefaultInfo(files = depset([build_file] + map.values())),
        PackageFilesInfo(dest_src_map = map),
    ]

aspect_lib = rule(
    implementation = _aspect_lib_impl,
    attrs = {"files": attr.label_list(allow_files = True)},
)
