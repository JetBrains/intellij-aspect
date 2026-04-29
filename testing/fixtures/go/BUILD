load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_go_0_60_0",
    "//testing/fixtures:toolchains_llvm_latest",
]

_ASPECTS = [
    "modules:go_info.bzl%intellij_go_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = ["//:all"],
    use_msys2 = True,
)
