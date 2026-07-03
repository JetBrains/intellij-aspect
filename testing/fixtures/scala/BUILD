load("//testing/rules:defs.bzl", "local_test_fixture", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_scala_7_1_6",
    "//testing/fixtures:toolchains_llvm_latest",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    bazel = ">=9",
    modules = _MODULES,
    rule_sets = ["scala"],
    targets = [
        "//src/test/com/example/foo:test",
    ],
    use_msys2 = True,
)
