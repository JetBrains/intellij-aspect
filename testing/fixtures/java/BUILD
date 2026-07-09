load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_java_8_16_1",
    "//testing/fixtures:rules_java_9_3_0",
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
    rule_sets = ["java"],
    targets = [
        "//:main",
        "//test/...",
    ],
    use_msys2 = True,
)

test_fixture(
    name = "top_level_toolchain",
    srcs = glob(["simple/**"]),
    modules = _MODULES,
    output_groups = _OUTPUT_GROUPS,
    rule_sets = ["java"],
    strip_prefix = "simple",
    targets = [
        "//:main",
        "//test/...",
        "@rules_java//toolchains:current_java_toolchain",
    ],
    use_msys2 = True,
)

test_fixture(
    name = "macro",
    srcs = glob(["macro/**"]),
    modules = _MODULES,
    rule_sets = ["java"],
    targets = ["//..."],
)

test_fixture(
    name = "plugin",
    srcs = glob(["plugin/**"]),
    modules = _MODULES,
    output_groups = _OUTPUT_GROUPS,
    rule_sets = ["java"],
    targets = ["//:java_lib"],
)

test_fixture(
    name = "forwardedArtifacts",
    srcs = glob(["forwardedArtifacts/**"]),
    modules = _MODULES,
    output_groups = _OUTPUT_GROUPS,
    rule_sets = ["java"],
    targets = ["//..."],
    use_msys2 = True,
)

test_fixture(
    name = "resources",
    srcs = glob(["resources/**"]),
    modules = _MODULES,
    output_groups = _OUTPUT_GROUPS,
    rule_sets = ["java"],
    targets = ["//:hello"],
    use_msys2 = True,
)
