load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//private:bazel_rules.bzl", "bazel_module")
load(":config.bzl", "test_config")

package(default_testonly = 1)

proto_library(
    name = "builder_proto",
    srcs = ["builder.proto"],
    deps = ["//private/proto:ide_info_proto"],
)

java_proto_library(
    name = "builder_java_proto",
    deps = [":builder_proto"],
)

kt_jvm_library(
    name = "builder_lib",
    srcs = ["builder.kt"],
    deps = [
        ":builder_java_proto",
        "//private/lib:exec",
        "@protobuf//:protobuf_java",
    ],
)

java_binary(
    name = "builder_bin",
    main_class = "com.intellij.aspect.testing.rules.BuilderKt",
    runtime_deps = [":builder_lib"],
)

bazel_module(
    name = "aspect_module",
    archive = "//:archive_bcr",
    module_name = "intellij_aspect",
    version = "test_build",
    visibility = ["//testing/fixtures:__subpackages__"],
)

kt_jvm_library(
    name = "fixture",
    srcs = ["fixture.kt"],
    visibility = ["//testing/tests:__subpackages__"],
    deps = [
        ":builder_java_proto",
        ":utils",
        "@maven//:junit_junit",
        "@protobuf//:protobuf_java",
        "@rules_java//java/runfiles",
    ],
)

kt_jvm_library(
    name = "utils",
    srcs = ["utils.kt"],
    visibility = ["//testing/tests:__subpackages__"],
    deps = [
        ":builder_java_proto",
        "@maven//:junit_junit",
        "@protobuf//:protobuf_java",
        "@rules_java//java/runfiles",
    ],
)
