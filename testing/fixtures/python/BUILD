load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_python_1_4_0",
    "//testing/fixtures:rules_python_1_6_3",
    "//testing/fixtures:rules_python_1_7_0",
    "//testing/fixtures:toolchains_llvm_latest",
]

_ASPECTS = [
    "modules:py_info.bzl%intellij_py_info_aspect",
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
    name = "args",
    srcs = glob(["args/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = ["//:all"],
)
