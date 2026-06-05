load("@bazel_jar_jar//:jar_jar.bzl", "jar_jar")
load("@bazel_skylib//rules:copy_file.bzl", "copy_file")
load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_pkg//pkg:mappings.bzl", "pkg_filegroup", "pkg_files")
load("@rules_pkg//pkg:pkg.bzl", "pkg_tar", "pkg_zip")
load("//private/rules:local_registry.bzl", "local_registry")

BCR_NAME = "intellij_aspect"

BCR_VERSION = "0.0.1"

exports_files(["MODULE.bazel.bcr"])

pkg_files(
    name = "bcr_module",
    srcs = ["MODULE.bazel.bcr"],
    renames = {"MODULE.bazel.bcr": "MODULE.bazel"},
)

pkg_filegroup(
    name = "bcr_sources",
    srcs = [
        ":bcr_module",
        "//common",
        "//config",
        "//intellij",
        "//modules",
    ],
)

# zip archive used for deployent from the IDE
pkg_zip(
    name = "archive_ide",
    srcs = [
        "//common",
        "//intellij",
        "//modules",
    ],
    visibility = ["//visibility:public"],
)

# tar archive used for deployment to the BCR
pkg_tar(
    name = "archive_bcr",
    srcs = [":bcr_sources"],
    extension = "tar.gz",
    package_dir = BCR_NAME,
    visibility = ["//visibility:public"],
)

# zip archive used for testing (tars are hard to process with kotlin)
pkg_zip(
    name = "archive_test",
    srcs = [":bcr_sources"],
    visibility = ["//testing:__subpackages__"],
)

# local BCR registry, used for local registry deployment from the IDE
local_registry(
    name = "local_deploy",
    archive = ":archive_bcr",
    module_file = "MODULE.bazel.bcr",
    module_name = BCR_NAME,
    module_version = BCR_VERSION,
    visibility = ["//visibility:public"],
)

# The full deploy jar, without the kotlin stdlib, before renaming any classes.
java_binary(
    name = "sdk_plain",
    create_executable = False,
    deploy_env = ["//third_party/kotlin:deploy_env"],
    runtime_deps = ["//sdk"],
)

# The sdk jar used to interact with the aspect. To avoid conflicts with other versions of protobuf,
# the protobuf infrastructure is renamed to be internal to this project, consumers are expected to provide
# the kotlin stdlib themselves on their runtime classpath.
jar_jar(
    name = "sdk",
    inline_rules = ["rule com.google.protobuf.** com.intellij.aspect.internal.protoinfra.@1"],
    input_jar = "//:sdk_plain_deploy.jar",
    output_jar = "sdk_deploy.jar",
)
