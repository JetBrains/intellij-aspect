load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")

package(default_visibility = ["//visibility:public"])

proto_library(
    name = "common_proto",
    srcs = ["common.proto"],
)

java_proto_library(
    name = "common_java_proto",
    deps = [":common_proto"],
)

proto_library(
    name = "ide_info_proto",
    srcs = ["ide_info.proto"],
    deps = [":common_proto"],
)

java_proto_library(
    name = "ide_info_java_proto",
    deps = [":ide_info_proto"],
)
