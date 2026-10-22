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
    "//testing/fixtures:rules_java_latest",
]

_MODULES_KSP = [
    "//testing/fixtures:rules_kotlin_latest",
    "//testing/fixtures:toolchains_llvm_latest",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    modules = _MODULES,
    outputs = "build",
    rule_sets = ["kotlin"],
    targets = [
        "//:main",
    ],
    use_msys2 = True,
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
    outputs = "build",
    rule_sets = ["kotlin"],
    targets = [
        "//:A",
    ],
)
