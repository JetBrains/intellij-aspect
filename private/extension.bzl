load("//private:bazel_repos.bzl", "bazel_binaries")

_bazel = tag_class(attrs = {"versions": attr.string_list()})

def _collect_bazel_versions(mctx):
    return [
        version
        for mod in mctx.modules
        for tag in mod.tags.bazel
        for version in tag.versions
    ]

def _registry_extension_impl(mctx):
    bazel_binaries(
        name = "registry_bazel",
        versions = _collect_bazel_versions(mctx),
    )

registry = module_extension(
    implementation = _registry_extension_impl,
    tag_classes = {"bazel": _bazel},
)
