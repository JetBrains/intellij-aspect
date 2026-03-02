load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "bep",
    srcs = ["bep.kt"],
    visibility = [
        "//testing:__subpackages__",
        "//tools:__subpackages__",
    ],
    deps = ["@maven//:com_fasterxml_jackson_core_jackson_databind"],
)

kt_jvm_library(
    name = "deploy",
    srcs = [
        "config.kt",
        "deploy.kt",
        "loads.kt",
        "transformers.kt",
    ],
    visibility = ["//visibility:public"],
)
