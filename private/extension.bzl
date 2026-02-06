load("//private:bazel_repos.bzl", "bazel_binaries", "bcr_archive")

_bazel = tag_class(attrs = {
    "versions": attr.string_list(),
})
_bcr = tag_class(attrs = {
    "commit": attr.string(mandatory = True),
    "sha256": attr.string(mandatory = True),
})

def _collect_bazel_versions(mctx):
    return [
        version
        for mod in mctx.modules
        for tag in mod.tags.bazel
        for version in tag.versions
    ]

def _collect_bcr_config(mctx):
    for mod in mctx.modules:
        for tag in mod.tags.bcr:
            return struct(commit = tag.commit, sha256 = tag.sha256)

    return None

def _registry_extension_impl(mctx):
    bazel_binaries(
        name = "registry_bazel",
        versions = _collect_bazel_versions(mctx),
    )

    bcr_config = _collect_bcr_config(mctx)
    if not bcr_config:
        fail("no bcr config porvided")

    bcr_archive(
        name = "registry_bcr",
        commit = bcr_config.commit,
        sha256 = bcr_config.sha256,
    )

registry = module_extension(
    implementation = _registry_extension_impl,
    tag_classes = {
        "bazel": _bazel,
        "bcr": _bcr,
    },
)
