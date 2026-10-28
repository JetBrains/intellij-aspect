load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//private/rules:java_resource_jar.bzl", "java_resource_jar")

# Package archive_ide.zip and the aspect definitions into a jar, every file at its relative path.
java_resource_jar(
    name = "sdk_resource",
    srcs = [
        "//:archive_ide",
        "//config:aspect_ide.textproto",
        "//config:cc_debug_aspect.textproto",
    ],
)

kt_jvm_library(
    name = "deploy",
    srcs = [
        "aspectchoice.kt",
        "config.kt",
        "definition.kt",
        "deploy.kt",
        "loads.kt",
        "transformers.kt",
        "utils.kt",
    ],
    resource_jars = [":sdk_resource"],
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
