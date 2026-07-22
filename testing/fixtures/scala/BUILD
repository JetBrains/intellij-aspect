load("//testing/rules:defs.bzl", "test_fixture")

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

test_fixture(
    name = "generated",
    srcs = glob(["generated/**"]),
    bazel = ">=9",
    modules = _MODULES,
    output_groups = [
        "intellij-sync",
        "intellij-build",
    ],
    rule_sets = ["scala"],
    targets = ["//:all"],
    use_msys2 = True,
)
