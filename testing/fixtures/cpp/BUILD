load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_cc_0_1_1",
    "//testing/fixtures:rules_cc_0_2_9",
    "//testing/fixtures:rules_cc_0_2_14",
    "//testing/fixtures:toolchains_llvm_latest",
]

_ASPECTS = [
    "modules:xcode_info.bzl%intellij_xcode_info_aspect",
    "modules:cc_info.bzl%intellij_cc_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = ["//:all"],
)

test_fixture(
    name = "transition",
    srcs = glob(["transition/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = ["//:all"],
)

test_fixture(
    name = "builtin",
    srcs = glob(["builtin/**"]),
    aspects = _ASPECTS,
    bazel = "<=8",
    builtin = True,
    modules = _MODULES,
    targets = ["//:main"],
)

test_fixture(
    name = "tool",
    srcs = glob(["tool/**"]),
    aspects = _ASPECTS,
    bazel = ">=8",
    modules = _MODULES,
    targets = ["//:tool"],
)
