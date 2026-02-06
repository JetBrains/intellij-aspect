_BUILD_FILE_HEADER = """
load("@@//private:bazel_rules.bzl", "bazel_binary")

package(default_visibility = ["//visibility:public"])
"""

_BUILD_FILE_BINARY = """
bazel_binary(
    name = "{name}",
    version = "{version}",
    executable = "{executable}",
)
"""

_BAZEL_URL_TEMPLATE = "https://github.com/bazelbuild/bazel/releases/download/{0}/bazel-{0}-{1}-{2}"

def _format_name(version):
    return version.replace(".", "_")

def _os_name(rctx):
    name = rctx.os.name.lower()

    if name.startswith("linux"):
        return "linux"
    if name.startswith("mac os"):
        return "darwin"
    if name.startswith("windows"):
        return "windows"

    fail("unrecognized os: %s" % name)

def _arch_name(rctx):
    arch = rctx.os.arch.lower()

    if arch.startswith("amd64") or arch.startswith("x86_64"):
        return "x86_64"
    if arch.startswith("aarch64") or arch.startswith("arm"):
        return "arm64"

    fail("unrecognized arch: %s" % arch)

def _bazel_repo_impl(rctx):
    content = _BUILD_FILE_HEADER

    for version in rctx.attr.versions:
        name = _format_name(version)
        executable = "%s_bin" % name

        rctx.download(
            _BAZEL_URL_TEMPLATE.format(version, _os_name(rctx), _arch_name(rctx)),
            output = executable,
            executable = True,
        )

        content += _BUILD_FILE_BINARY.format(
            name = name,
            version = version,
            executable = executable,
        )

    rctx.file("BUILD", content)

# A repository to store multiple bazel versions that can be executed on the
# local host.
bazel_binaries = repository_rule(
    implementation = _bazel_repo_impl,
    attrs = {"versions": attr.string_list(mandatory = True)},
)

_BCR_URL_TEMPLATE = "https://github.com/bazelbuild/bazel-central-registry/archive/{0}.zip"

_BCR_BUILD_FILE = """
package(default_visibility = ["//visibility:public"])

# Expose the BCR zip file
exports_files(["bcr.zip"])

filegroup(
    name = "bcr",
    srcs = ["bcr.zip"],
)
"""

def _bcr_archive_impl(rctx):
    url = _BCR_URL_TEMPLATE.format(rctx.attr.commit)

    # Download zip WITHOUT extracting
    rctx.download(
        url = url,
        output = "bcr.zip",
        sha256 = rctx.attr.sha256,
    )

    rctx.file("BUILD", _BCR_BUILD_FILE)

# A repository to download a specific commit of the Bazel Central Registry
bcr_archive = repository_rule(
    implementation = _bcr_archive_impl,
    attrs = {
        "commit": attr.string(mandatory = True, doc = "Git commit SHA of the BCR to download"),
        "sha256": attr.string(mandatory = True, doc = "SHA256 checksum of the downloaded zip file"),
    },
)
