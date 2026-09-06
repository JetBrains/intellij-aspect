load("@rules_java//java:defs.bzl", "java_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

java_library(
    name = "runfiles_repo",
    srcs = ["RunfilesRepo.java"],
    visibility = ["//tools:__subpackages__"],
    deps = ["@rules_java//java/runfiles"],
)

kt_jvm_library(
    name = "cli",
    srcs = ["cli.kt"],
    visibility = ["//tools:__subpackages__"],
    deps = [
        "//sdk/lib:deploy",
        "@maven//:org_jetbrains_kotlinx_kotlinx_cli_jvm",
    ],
)
