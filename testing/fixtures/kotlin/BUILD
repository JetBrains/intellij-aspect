load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_kotlin_2_3_20",
    "//testing/fixtures:rules_kotlin_2_2_0",
    "//testing/fixtures:toolchains_llvm_latest",
]

_ASPECTS = [
    "modules:kotlin_info.bzl%intellij_kotlin_info_aspect",
    # order is important, run jvm_info and java_common after all JVM-languages
    "modules:jvm_info.bzl%intellij_jvm_info_aspect",
    "modules:java_common_info.bzl%intellij_java_common_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = [
        "//:main",
    ],
)

test_fixture(
    name = "associates",
    srcs = glob(["associates/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = [
        "//:A",
    ],
)
