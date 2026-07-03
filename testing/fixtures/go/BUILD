load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_go_0_61_1",
    "//testing/fixtures:toolchains_llvm_latest",
]

# Old version of rules_go, not compatible with newer versions of bazel, but still in use
_MODULES_OLD = [
    "//testing/fixtures:rules_go_0_49_0",
    "//testing/fixtures:toolchains_llvm_latest",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    modules = _MODULES,
    rule_sets = ["go"],
    targets = [
        "//:all",
        "//testa",
    ],
    use_msys2 = True,
)

test_fixture(
    name = "simple_old",
    srcs = glob(["simple/**"]),
    bazel = "7",
    modules = _MODULES_OLD,
    rule_sets = ["go"],
    strip_prefix = "simple",
    targets = [
        "//:all",
        "//testa",
    ],
    use_msys2 = True,
)
