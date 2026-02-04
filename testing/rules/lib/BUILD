load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//testing/rules/cache:cache.bzl", "repo_cache")

package(
    default_testonly = 1,
    default_visibility = ["//testing/rules:__subpackages__"],
)

proto_library(
    name = "action_lib_proto",
    srcs = ["action.proto"],
)

java_proto_library(
    name = "action_lib_java_proto",
    visibility = ["//visibility:private"],
    deps = [":action_lib_proto"],
)

kt_jvm_library(
    name = "zip_lib",
    srcs = ["zip.kt"],
)

kt_jvm_library(
    name = "action_lib",
    srcs = ["action.kt"],
    exports = [
        ":action_lib_java_proto",
        "@protobuf//:protobuf_java",
    ],
    deps = [
        ":action_lib_java_proto",
        ":zip_lib",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@protobuf//:protobuf_java",
    ],
)

kt_jvm_library(
    name = "test_utils_lib",
    srcs = ["test_utils.kt"],
    visibility = ["//testing/tests:__subpackages__"],
    deps = ["@maven//:junit_junit"],
)
