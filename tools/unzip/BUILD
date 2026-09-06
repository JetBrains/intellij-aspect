load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "unzip_lib",
    srcs = ["main.kt"],
    visibility = ["//visibility:private"],
    deps = ["//private/lib:zip"],
)

java_binary(
    name = "unzip",
    main_class = "com.intellij.aspect.tools.unzip.MainKt",
    visibility = ["//testing:__subpackages__"],
    runtime_deps = [":unzip_lib"],
)
