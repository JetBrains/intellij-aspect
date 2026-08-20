load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//config:aspect.bzl", "MODULES")
load("//private/rules:kt_aspect_enum.bzl", "kt_aspect_enum")
load("//modules:rules_proto_info.bzl", rules_proto_info = "module")

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

kt_aspect_enum(
    name = "aspectenum",
    modules = MODULES + [rules_proto_info], # legacy rules proto are not supported for BCR depolyment
    package = "com.intellij.aspect.lib",
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
        ":aspectenum",
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
