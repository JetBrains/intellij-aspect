load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

# Package archive_ide.zip and the aspect definition into a jar with the files at the jar root.
#
# The in-jar resource path breaks when consumed as an external repo, so
# resources/resource_strip_prefix can't be used and a workaround is needed.
genrule(
    name = "sdk_resource_jar",
    srcs = [
        "//:archive_ide",
        "//config:aspect_ide.textproto",
    ],
    outs = ["sdk_resource.jar"],
    cmd = " ".join([
        "$(execpath @bazel_tools//tools/zip:zipper) c $@",
        "archive_ide.zip=$(execpath //:archive_ide)",
        "intellij_aspect.textproto=$(execpath //config:aspect_ide.textproto)",
    ]),
    tools = ["@bazel_tools//tools/zip:zipper"],
)

kt_jvm_library(
    name = "deploy",
    srcs = [
        "AspectDefinition.kt",
        "aspectchoice.kt",
        "config.kt",
        "deploy.kt",
        "loads.kt",
        "transformers.kt",
        "utils.kt",
    ],
    resource_jars = [":sdk_resource_jar"],
    visibility = ["//visibility:public"],
    deps = [
        "//private/lib:path",
        "//private/lib:proto",
        "//sdk/proto:aspect_java_proto",
        "@protobuf//:protobuf_java",
    ],
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
