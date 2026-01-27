load("@bazel_skylib//rules:copy_file.bzl", "copy_file")
load("@rules_pkg//pkg:pkg.bzl", "pkg_tar", "pkg_zip")
load("//private:local_registry.bzl", "local_registry")

BCR_NAME = "intellij_aspect"

BCR_VERSION = "0.0.1"

copy_file(
    name = "module_bcr",
    src = "MODULE.bazel.bcr",
    out = "MODULE.bazel",
)

pkg_zip(
    name = "archive_ide",
    srcs = [
        "//common",
        "//intellij",
        "//modules",
    ],
    visibility = ["//visibility:public"],
)

pkg_tar(
    name = "archive_bcr",
    srcs = [
        ":module_bcr",
        "//common",
        "//config",
        "//intellij",
        "//modules",
    ],
    extension = "tar.gz",
    package_dir = BCR_NAME,
    visibility = ["//visibility:public"],
)

local_registry(
    name = "local_deploy",
    archive = ":archive_bcr",
    module_file = ":module_bcr",
    module_name = BCR_NAME,
    module_version = BCR_VERSION,
)
