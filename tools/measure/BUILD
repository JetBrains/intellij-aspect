load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "report_proto",
    srcs = ["report.proto"],
    visibility = ["//visibility:private"],
)

java_proto_library(
    name = "report_java_proto",
    visibility = ["//visibility:private"],
    deps = [":report_proto"],
)

kt_jvm_library(
    name = "measure_lib",
    srcs = [
        "analysis.kt",
        "main.kt",
        "metrics.kt",
    ],
    visibility = ["//visibility:private"],
    deps = [
        ":report_java_proto",
        "//private/lib:logger",
        "//private/lib:sandbox",
        "//private/lib:tee",
        "//sdk/lib:deploy",
        "//tools/lib:cli",
        "//tools/lib:runfiles_repo",
        "@maven//:org_jetbrains_kotlinx_kotlinx_cli_jvm",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
        "@protobuf//:protobuf_java",
    ],
)

java_binary(
    name = "measure",
    data = ["@bazelisk//:executable"],
    main_class = "com.intellij.aspect.tools.measure.MainKt",
    visibility = ["//visibility:public"],
    runtime_deps = [":measure_lib"],
)

kt_jvm_library(
    name = "benchmark_lib",
    srcs = ["benchmark.kt"],
    visibility = ["//visibility:private"],
    deps = [
        ":report_java_proto",
        "@maven//:com_google_code_gson_gson",
        "@protobuf//:protobuf_java",
    ],
)

java_binary(
    name = "benchmark",
    main_class = "com.intellij.aspect.tools.measure.BenchmarkKt",
    visibility = ["//visibility:public"],
    runtime_deps = [":benchmark_lib"],
)
