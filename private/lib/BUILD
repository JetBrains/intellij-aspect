load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

package(
    default_visibility = [
        "//sdk:__subpackages__",
        "//testing:__subpackages__",
        "//tools:__subpackages__",
    ],
)

kt_jvm_library(
    name = "proto",
    srcs = ["proto.kt"],
    deps = ["@protobuf//java/core"],
)

kt_jvm_library(
    name = "zip",
    srcs = ["zip.kt"],
)

kt_jvm_library(
    name = "tee",
    srcs = ["tee.kt"],
)

kt_jvm_library(
    name = "logger",
    srcs = ["logger.kt"],
    deps = [":tee"],
)

kt_jvm_library(
    name = "sandbox",
    srcs = ["sandbox.kt"],
    deps = [
        ":bep",
        ":logger",
        ":path",
        ":platform",
        ":tee",
        ":zip",
        "//sdk/lib:deploy",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)

kt_jvm_library(
    name = "path",
    srcs = ["path.kt"],
)

kt_jvm_library(
    name = "bep",
    srcs = ["bep.kt"],
    deps = ["@maven//:com_fasterxml_jackson_core_jackson_databind"],
)

kt_jvm_library(
    name = "platform",
    srcs = ["platform.kt"],
)
