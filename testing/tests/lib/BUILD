load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//testing/rules:defs.bzl", "junit_test")

kt_jvm_library(
    name = "test_utils_lib",
    srcs = ["test_utils.kt"],
    visibility = ["//testing/tests:__subpackages__"],
)

junit_test(
    data = ["//:archive_ide"],
    env = {
        "ARCHIVE_IDE": "$(rlocationpaths //:archive_ide)",
    },
    test = "DeployTest.kt",
    deps = [
        "//private/lib:deploy",
        "@rules_java//java/runfiles",
    ],
)
