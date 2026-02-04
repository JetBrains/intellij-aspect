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
    name = "builder_proto",
    srcs = ["builder.proto"],
    deps = [
        "//private/proto:ide_info_proto",
        "//testing/rules/lib:action_lib_proto",
    ],
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
        "//testing/rules/lib:action_lib",
        "//testing/rules/lib:zip_lib",
    ],
)

java_binary(
    name = "builder_bin",
    main_class = "com.intellij.aspect.testing.rules.fixture.BuilderKt",
    runtime_deps = [":builder_lib"],
)

kt_jvm_library(
    name = "fixture_lib",
    srcs = ["fixture.kt"],
    visibility = ["//testing/tests:__subpackages__"],
    deps = [
        ":builder_java_proto",
        "@maven//:junit_junit",
        "@protobuf//:protobuf_java",
        "@rules_java//java/runfiles",
    ],
)
