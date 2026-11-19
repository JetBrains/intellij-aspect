load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "proto2json_lib",
    srcs = ["main.kt"],
    visibility = ["//visibility:private"],
    deps = [
        "//sdk/proto:aspect_java_proto",
        "//sdk/proto:common_java_proto",
        "//sdk/proto:ide_info_java_proto",
        "//testing/rules/fixture:fixture_java_proto",
        "//tools/lib:cli",
        "@maven//:org_jetbrains_kotlinx_kotlinx_cli_jvm",
        "@protobuf//:protobuf_java",
        "@protobuf//java/util",
    ],
)

java_binary(
    name = "proto2json",
    main_class = "com.intellij.aspect.tools.proto2json.MainKt",
    runtime_deps = [":proto2json_lib"],
)
