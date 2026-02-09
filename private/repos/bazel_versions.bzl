_BAZEL_RELEASE_URL = "https://github.com/bazelbuild/bazel/releases/download/{version}/bazel-{version}-{os}-{arch}"

_BUILD_FILE_HEADER = """
load("@@//private/rules:bazel_binary.bzl", "bazel_binary")

package(default_visibility = ["//visibility:public"])
"""

_BUILD_FILE_BINARY = """
bazel_binary(
    name = "{name}",
    version = "{version}",
    executable = "{executable}",
)
"""

def _os_name(rctx):
    """Normalize OS name for download URLs."""
    name = rctx.os.name.lower()
    if name.startswith("linux"):
        return "linux"
    if name.startswith("mac os"):
        return "darwin"
    if name.startswith("windows"):
        return "windows"
    fail("unrecognized os: %s" % name)

def _arch_name(rctx):
    """Normalize architecture name for download URLs."""
    arch = rctx.os.arch.lower()
    if arch.startswith("amd64") or arch.startswith("x86_64"):
        return "x86_64"
    if arch.startswith("aarch64") or arch.startswith("arm"):
        return "arm64"
    fail("unrecognized arch: %s" % arch)

def _format_version_label(version):
    """Convert version string to valid Bazel label."""
    return version.replace(".", "_")

def _bazel_versions_impl(rctx):
    content = _BUILD_FILE_HEADER

    for version in rctx.attr.versions:
        name = _format_version_label(version)
        executable = "%s_bin" % name

        url = _BAZEL_RELEASE_URL.format(
            version = version,
            os = _os_name(rctx),
            arch = _arch_name(rctx),
        )

        rctx.download(
            url = url,
            output = executable,
            executable = True,
        )

        content += _BUILD_FILE_BINARY.format(
            name = name,
            version = version,
            executable = executable,
        )

    rctx.file("BUILD", content)

bazel_versions = repository_rule(
    implementation = _bazel_versions_impl,
    attrs = {"versions": attr.string_list(mandatory = True)},
    doc = "Downloads multiple Bazel versions for local execution.",
)
