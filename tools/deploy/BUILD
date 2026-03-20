load("@rules_java//java:defs.bzl", "java_binary")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "deploy_lib",
    srcs = ["main.kt"],
    visibility = ["//visibility:private"],
    deps = [
        "//sdk/lib:deploy",
        "//tools/lib:exec",
        "//tools/lib:runfiles_repo",
        "@maven//:org_jetbrains_kotlinx_kotlinx_cli_jvm",
    ],
)

java_binary(
    name = "deploy",
    data = [
        "//:archive_bcr",
        "//:archive_ide",
    ],
    main_class = "com.intellij.aspect.tools.deploy.MainKt",
    runtime_deps = [":deploy_lib"],
)
