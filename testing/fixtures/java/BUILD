load("//testing/rules:defs.bzl", "local_test_fixture", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_java_8_16_1",
    "//testing/fixtures:rules_java_9_3_0",
    "//testing/fixtures:toolchains_llvm_latest",
]

_ASPECTS = [
    "modules:java_info.bzl%intellij_java_info_aspect",
    # order is important, run jvm_info and java_common after all JVM-languages
    "modules:jvm_info.bzl%intellij_jvm_info_aspect",
    "modules:java_common_info.bzl%intellij_java_common_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

_OUTPUT_GROUPS = [
    "intellij-sync-java",
    "intellij-build-java",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    output_groups = _OUTPUT_GROUPS,
    targets = [
        "//:main",
        "//test/...",
    ],
    use_msys2 = True,
)

test_fixture(
    name = "macro",
    srcs = glob(["macro/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = ["//..."],
)

test_fixture(
    name = "plugin",
    srcs = glob(["plugin/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = ["//:java_lib"],
)

local_test_fixture(
    name = "maven",
    deps = [
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_truth_truth",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
