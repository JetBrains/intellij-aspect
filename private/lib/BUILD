load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "utils",
    srcs = glob(["utils/*.kt"]),
    visibility = [
        "//testing:__subpackages__",
        "//tools:__subpackages__",
    ],
    deps = [
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@protobuf//java/core",
    ],
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
