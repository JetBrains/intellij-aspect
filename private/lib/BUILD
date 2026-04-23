load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(
    default_visibility = [
        "//testing:__subpackages__",
        "//tools:__subpackages__",
    ],
)

kt_jvm_library(
    name = "proto",
    srcs = ["proto.kt"],
    deps = ["@protobuf//java/core"],
)

kt_jvm_library(
    name = "zip",
    srcs = ["zip.kt"],
)

kt_jvm_library(
    name = "tee",
    srcs = ["tee.kt"],
)

kt_jvm_library(
    name = "path",
    srcs = ["path.kt"],
)

kt_jvm_library(
    name = "bep",
    srcs = ["bep.kt"],
    deps = ["@maven//:com_fasterxml_jackson_core_jackson_databind"],
)
