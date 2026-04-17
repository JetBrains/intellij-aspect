load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

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

proto_library(
    name = "builder_proto",
    srcs = ["builder.proto"],
    deps = [":fixture_proto"],
)

java_proto_library(
    name = "builder_java_proto",
    deps = [":builder_proto"],
)

kt_jvm_library(
    name = "fixture_lib",
    srcs = ["fixture.kt"],
    visibility = ["//testing/tests:__subpackages__"],
    deps = [
        ":fixture_java_proto",
        "@maven//:junit_junit",
        "@protobuf//:protobuf_java",
        "@rules_java//java/runfiles",
    ],
)

kt_jvm_library(
    name = "builder_lib",
    srcs = ["builder.kt"],
    visibility = ["//testing/tests:__subpackages__"],
    deps = [
        ":builder_java_proto",
        "//private/lib:bep",
        "//private/lib:proto",
        "@protobuf//:protobuf_java",
    ],
)

java_binary(
    name = "builder_bin",
    main_class = "com.intellij.aspect.testing.rules.fixture.BuilderKt",
    visibility = ["//testing/fixtures:__subpackages__"],
    runtime_deps = [":builder_lib"],
)
