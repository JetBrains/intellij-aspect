load("@protobuf//bazel:java_proto_library.bzl", "java_proto_library")
load("@protobuf//bazel:proto_library.bzl", "proto_library")
load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

proto_library(
    name = "worker_proto",
    srcs = ["worker.proto"],
    deps = ["//testing/rules/fixture:fixture_proto"],
)

java_proto_library(
    name = "worker_java_proto",
    deps = [":worker_proto"],
)

kt_jvm_library(
    name = "builder_lib",
    srcs = [
        "builder.kt",
        "sandbox.kt",
        "worker.kt",
    ],
    visibility = ["//testing/rules:__subpackages__"],
    deps = [
        ":worker_java_proto",
        "//private/lib:bep",
        "//private/lib:path",
        "//private/lib:platform",
        "//private/lib:proto",
        "//private/lib:tee",
        "//private/lib:zip",
        "//sdk/lib:deploy",
        "//testing/rules/fixture:fixture_java_proto",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

java_binary(
    name = "builder_bin",
    main_class = "com.intellij.aspect.testing.rules.worker.BuilderKt",
    visibility = ["//testing/fixtures:__subpackages__"],
    runtime_deps = [":builder_lib"],
)
