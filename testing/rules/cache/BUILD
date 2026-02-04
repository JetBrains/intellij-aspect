load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//testing/rules/cache:cache.bzl", "repo_cache")

package(default_testonly = 1)

proto_library(
    name = "builder_proto",
    srcs = ["builder.proto"],
    deps = ["//testing/rules/lib:action_lib_proto"],
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
    ],
)

java_binary(
    name = "builder_bin",
    main_class = "com.intellij.aspect.testing.rules.cache.BuilderKt",
    runtime_deps = [":builder_lib"],
)
