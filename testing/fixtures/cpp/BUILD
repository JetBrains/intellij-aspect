load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_cc_0_1_1",
    "//testing/fixtures:rules_cc_0_2_9",
    "//testing/fixtures:rules_cc_0_2_14",
    "//testing/fixtures:toolchains_llvm_latest",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    modules = _MODULES,
    rule_sets = ["cc"],
    targets = ["//:all"],
    use_msys2 = True,
)

test_fixture(
    name = "transition",
    srcs = glob(["transition/**"]),
    modules = _MODULES,
    rule_sets = ["cc"],
    targets = ["//:all"],
)

test_fixture(
    name = "builtin",
    srcs = glob(["builtin/**"]),
    bazel = "<=8",
    builtin = True,
    modules = _MODULES,
    rule_sets = ["cc"],
    targets = ["//:main"],
)

test_fixture(
    name = "tool",
    srcs = glob(["tool/**"]),
    bazel = ">=8",
    modules = _MODULES,
    rule_sets = ["cc"],
    targets = ["//:tool"],
)

test_fixture(
    name = "custom_toolchain",
    srcs = glob(["custom_toolchain/**"]),
    extra_flags = ["--extra_toolchains=//:sdp_toolchain"],
    modules = ["//testing/fixtures:rules_cc_latest"],
    rule_sets = ["cc"],
    targets = ["//:main"],
)

test_fixture(
    name = "layout_default",
    srcs = glob(["layout/**"]),
    bazel = ">=8",
    extra_flags = [
        "--noexperimental_sibling_repository_layout",
        "--inject_repository=external_module=module",
    ],
    modules = _MODULES,
    rule_sets = ["cc"],
    strip_prefix = "layout",
    targets = ["//main:main"],
)

test_fixture(
    name = "layout_sibling",
    srcs = glob(["layout/**"]),
    bazel = ">=8",
    extra_flags = [
        "--experimental_sibling_repository_layout",
        "--inject_repository=external_module=module",
    ],
    modules = _MODULES,
    rule_sets = ["cc"],
    strip_prefix = "layout",
    targets = ["//main:main"],
)

filegroup(
    name = "layout",
    testonly = 1,
    srcs = [
        ":layout_default",
        ":layout_sibling",
    ],
)

test_fixture(
    name = "protobuf",
    srcs = glob(["protobuf/**"]),
    bazel = ">=8",
    modules = _MODULES + [
        "//testing/fixtures:protobuf_34_1",
    ],
    rule_sets = ["cc"],
    targets = ["//main:main"],
)
