load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "deploy",
    srcs = [
        "aspectchoice.kt",
        "config.kt",
        "deploy.kt",
        "loads.kt",
        "transformers.kt",
    ],
    resources = ["//:archive_ide"],
    visibility = ["//visibility:public"],
    deps = ["//private/lib:path"],
)
