load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "apidump_lib",
    srcs = ["main.kt"],
    visibility = ["//testing/tests/sdk:__pkg__"],
)

java_binary(
    name = "apidump",
    main_class = "com.intellij.aspect.tools.apidump.MainKt",
    visibility = ["//testing/tests/sdk:__pkg__"],
    runtime_deps = [":apidump_lib"],
)
