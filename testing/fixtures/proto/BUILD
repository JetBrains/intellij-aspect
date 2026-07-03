load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_java_9_3_0",
    "//testing/fixtures:protobuf_34_1",
    "//testing/fixtures:toolchains_llvm_latest",
]

_RULES_PROTO_MODULES = [
    "//testing/fixtures:rules_java_9_3_0",
    "//testing/fixtures:rules_proto_7_1_0",
    "//testing/fixtures:toolchains_llvm_latest",
]

_OUTPUT_GROUPS = [
    "intellij-sync",
    "intellij-build",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    bazel = ">=8",  # only very old versions of protobuf support bazel 7
    modules = _MODULES,
    output_groups = select(
        {
            "@bazel_tools//src/conditions:windows": [],
            "//conditions:default": _OUTPUT_GROUPS,
        },
    ),
    rule_sets = [
        "proto",
        "java",
    ],
    targets = [
        "//...",
    ],
)

test_fixture(
    name = "rules_proto_simple",
    srcs = glob(["rules_proto_simple/**"]),
    bazel = "8",  # rules_proto is not bazel-9 ready
    bcr = False,
    modules = _RULES_PROTO_MODULES,
    output_groups = select(
        {
            "@bazel_tools//src/conditions:windows": [],
            "//conditions:default": _OUTPUT_GROUPS,
        },
    ),
    rule_sets = [
        "legacy_rules_proto",
        "java",
    ],
    targets = [
        "//...",
    ],
)
