load("@rules_java//java:defs.bzl", "java_library")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

java_library(
    name = "runfiles_repo",
    srcs = ["RunfilesRepo.java"],
    visibility = ["//tools:__subpackages__"],
    deps = ["@rules_java//java/runfiles"],
)

kt_jvm_library(
    name = "exec",
    srcs = ["exec.kt"],
    visibility = ["//tools:__subpackages__"],
    deps = [
        "//private/lib:bep",
        "//sdk/lib:deploy",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
    ],
)
