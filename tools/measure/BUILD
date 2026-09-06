load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
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
    srcs = glob(["*.kt"]),
    visibility = ["//visibility:private"],
    deps = [
        ":report_java_proto",
        "@protobuf//:protobuf_java",
    ],
)
