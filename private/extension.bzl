load("//private/repos:bazel_versions.bzl", "bazel_versions")
load("//private/repos:bcr_archive.bzl", "bcr_archive")

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

def _bazel_registry_impl(mctx):
    bazel_versions(
        name = "bazel_versions",
        versions = _collect_bazel_versions(mctx),
    )

    bcr_config = _collect_bcr_config(mctx)
    if not bcr_config:
        fail("no bcr config provided")

    bcr_archive(
        name = "bcr_archive",
        commit = bcr_config.commit,
        sha256 = bcr_config.sha256,
    )

bazel_registry = module_extension(
    implementation = _bazel_registry_impl,
    tag_classes = {
        "bazel": _bazel,
        "bcr": _bcr,
    },
)
