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

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    modules = _MODULES,
    rule_sets = [
        "cc",
        "java",
        "python",
    ],
    targets = ["//:all"],
)
