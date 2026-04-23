load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "differ_lib",
    srcs = glob(["*.kt"]),
    visibility = ["//visibility:private"],
    deps = [
        "//sdk",
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
        "//:archive_ide",
    ],
    main_class = "com.intellij.aspect.tools.differ.MainKt",
    runtime_deps = [":differ_lib"],
)
