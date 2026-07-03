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

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    modules = _MODULES,
    rule_sets = ["python"],
    targets = ["//:all"],
    use_msys2 = True,
)

test_fixture(
    name = "args",
    srcs = glob(["args/**"]),
    modules = _MODULES,
    rule_sets = ["python"],
    targets = ["//:all"],
)

test_fixture(
    name = "withImports",
    srcs = glob(["withImports/**"]),
    modules = _MODULES,
    rule_sets = ["python"],
    targets = ["//:main"],
)
