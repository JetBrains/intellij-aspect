load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("@rules_python//python:py_binary.bzl", "py_binary")

kt_jvm_library(
    name = "deploy",
    srcs = ["deploy.kt"],
    visibility = ["//visibility:public"],
)

kt_jvm_library(
    name = "exec",
    srcs = ["exec.kt"],
    visibility = [
        "//testing:__subpackages__",
        "//tools:__subpackages__",
    ],
    deps = ["@maven//:com_fasterxml_jackson_core_jackson_databind"],
)

py_binary(
    name = "local_registry",
    srcs = ["local_registry.py"],
    visibility = ["//visibility:public"],
)
