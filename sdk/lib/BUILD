load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

# Package archive_ide.zip into a jar with the file at the jar root.
#
# The in-jar resource path breaks when consumed as an external repo, so
# resources/resource_strip_prefix can't be used and a workaround is needed.
genrule(
    name = "sdk_resource_jar",
    srcs = ["//:archive_ide"],
    outs = ["sdk_resource.jar"],
    cmd = "$(execpath @bazel_tools//tools/zip:zipper) c $@ archive_ide.zip=$(execpath //:archive_ide)",
    tools = ["@bazel_tools//tools/zip:zipper"],
)

kt_jvm_library(
    name = "deploy",
    srcs = [
        "aspectchoice.kt",
        "config.kt",
        "deploy.kt",
        "loads.kt",
        "transformers.kt",
        "utils.kt",
    ],
    resource_jars = [":sdk_resource_jar"],
    visibility = ["//visibility:public"],
    deps = ["//private/lib:path"],
)

kt_jvm_library(
    name = "read",
    srcs = [
        "readtarget.kt",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//sdk/proto:ide_info_java_proto",
        "@protobuf//:protobuf_java",
    ],
)
