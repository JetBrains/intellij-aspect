_BCR_ARCHIVE_URL = "https://github.com/bazelbuild/bazel-central-registry/archive/{commit}.zip"

_BUILD_FILE = """
package(default_visibility = ["//visibility:public"])

exports_files(["bcr.zip"])

filegroup(
    name = "bcr",
    srcs = ["bcr.zip"],
)
"""

def _bcr_archive_impl(rctx):
    url = _BCR_ARCHIVE_URL.format(commit = rctx.attr.commit)

    # Download zip WITHOUT extracting - used for offline builds
    rctx.download(
        url = url,
        output = "bcr.zip",
        sha256 = rctx.attr.sha256,
    )

    rctx.file("BUILD", _BUILD_FILE)

bcr_archive = repository_rule(
    implementation = _bcr_archive_impl,
    attrs = {
        "commit": attr.string(
            mandatory = True,
            doc = "Git commit SHA of the BCR to download",
        ),
        "sha256": attr.string(
            mandatory = True,
            doc = "SHA256 checksum of the downloaded zip file",
        ),
    },
    doc = "Downloads a specific BCR commit as a zip file for offline builds.",
)
