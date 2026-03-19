load("//testing/rules:defs.bzl", "test_fixture")

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
    "intellij:aspect.bzl%intellij_info_aspect",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = ["//:main"],
)

test_fixture(
    name = "macro",
    srcs = glob(["macro/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = ["//..."],
)
