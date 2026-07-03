load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_kotlin_2_3_20",
    "//testing/fixtures:toolchains_llvm_latest",
] + select({
    # rules_kotlin 2.2.x can fail on Windows with: LAUNCHER ERROR: Rlocation failed on ... annotations-13.0.jar
    # https://github.com/bazel-contrib/rules_kotlin/issues/1300
    "@bazel_tools//src/conditions:windows": [],
    "//conditions:default": ["//testing/fixtures:rules_kotlin_2_2_0"],
})

_MOUDLES_WITH_JAVA = _MODULES + [
    "//testing/fixtures:rules_java_9_3_0",
]

_MODULES_KSP = [
    "//testing/fixtures:rules_kotlin_2_4_0",
    "//testing/fixtures:toolchains_llvm_latest",
]

_OUTPUT_GROUPS = [
    "intellij-sync",
    "intellij-build",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    modules = _MODULES,
    output_groups = _OUTPUT_GROUPS,
    rule_sets = ["kotlin"],
    targets = [
        "//:main",
    ],
)

test_fixture(
    name = "associates",
    srcs = glob(["associates/**"]),
    modules = _MODULES,
    rule_sets = ["kotlin"],
    targets = [
        "//:A",
    ],
)

test_fixture(
    name = "resources",
    srcs = glob(["resources/**"]),
    modules = _MOUDLES_WITH_JAVA,
    rule_sets = [
        "kotlin",
        "java",
    ],
    targets = [
        "//module:a-lib",
    ],
)

test_fixture(
    name = "ksp",
    srcs = glob(["ksp/**"]),
    modules = _MODULES_KSP,
    rule_sets = ["kotlin"],
    targets = [
        "//:A",
    ],
)
