load("//testing/rules:defs.bzl", "test_fixture", "test_matrix")
load(":defs.bzl", "test_matrix_cc")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

test_matrix_cc(
    name = "simple_matrix",
    bazel = [
        "@bazel_versions//:7_7_0",
        "@bazel_versions//:8_6_0",
        "@bazel_versions//:9_0_0",
    ],
)

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    configs = [":simple_matrix"],
    export_cache = "repo_cache",
    targets = [
        "//:main",
        "//:test",
        "//lib",
    ],
)

test_fixture(
    name = "transition",
    srcs = glob(["transition/**"]),
    configs = [":simple_matrix"],
    import_cache = ":repo_cache",
    targets = [
        "//:main",
        "//:foo_bar",
    ],
)

test_matrix_cc(
    name = "builtin_matrix",
    bazel = [
        "@bazel_versions//:7_7_0",
        "@bazel_versions//:8_6_0",
    ],
    builtin = True,
)

test_fixture(
    name = "builtin",
    srcs = glob(["builtin/**"]),
    configs = [":builtin_matrix"],
    import_cache = ":repo_cache",
    targets = ["//:main"],
)

test_matrix_cc(
    name = "tool_matrix",
    bazel = [
        "@bazel_versions//:8_6_0",
        "@bazel_versions//:9_0_0",
    ],
)

test_fixture(
    name = "tool",
    srcs = glob(["tool/**"]),
    configs = [":tool_matrix"],
    import_cache = ":repo_cache",
    targets = ["//:tool"],
)
