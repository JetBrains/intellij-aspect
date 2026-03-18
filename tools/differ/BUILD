load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "differ_lib",
    srcs = glob(["*.kt"]),
    visibility = ["//visibility:private"],
    deps = [
        "//private/lib:deploy",
        "//private/lib:utils",
        "//private/proto:common_java_proto",
        "//private/proto:ide_info_java_proto",
        "//tools/lib:exec",
        "//tools/lib:runfiles_repo",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:org_jetbrains_kotlinx_kotlinx_cli_jvm",
        "@protobuf//:protobuf_java",
    ],
)

java_binary(
    name = "differ",
    data = [
        ":legacy_aspect.zip",
        "//:archive_ide",
    ],
    main_class = "com.intellij.aspect.tools.differ.MainKt",
    runtime_deps = [":differ_lib"],
)
