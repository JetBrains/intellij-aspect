load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "utils_lib",
    testonly = 1,
    srcs = [
        "assert.kt",
        "fixture.kt",
        "utils.kt",
    ],
    visibility = ["//testing/tests:__subpackages__"],
    deps = [
        "//private/lib:path",
        "//sdk/proto:ide_info_java_proto",
        "//testing/rules/fixture:fixture_lib",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
    ],
)
