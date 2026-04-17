load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_cc_latest",
    "//testing/fixtures:rules_python_latest",
    "//testing/fixtures:rules_java_latest",
    "//testing/fixtures:toolchains_llvm_latest",
]

_ASPECTS = [
    "modules:xcode_info.bzl%intellij_xcode_info_aspect",
    "modules:cc_info.bzl%intellij_cc_info_aspect",
    "modules:java_info.bzl%intellij_java_info_aspect",
    "modules:jvm_info.bzl%intellij_jvm_info_aspect",
    "modules:java_common_info.bzl%intellij_java_common_info_aspect",
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
