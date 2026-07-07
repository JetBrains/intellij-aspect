load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(default_testonly = True)

proto_library(
    name = "fixture_proto",
    srcs = ["fixture.proto"],
    visibility = ["//testing/rules:__subpackages__"],
    deps = ["//sdk/proto:ide_info_proto"],
)

java_proto_library(
    name = "fixture_java_proto",
    visibility = ["//testing/rules:__subpackages__"],
    deps = [":fixture_proto"],
)

kt_jvm_library(
    name = "fixture_lib",
    srcs = ["fixture.kt"],
    visibility = ["//testing:__subpackages__"],
    deps = [
        ":fixture_java_proto",
        "//sdk/lib:deploy",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@protobuf//:protobuf_java",
        "@rules_java//java/runfiles",
    ],
)
