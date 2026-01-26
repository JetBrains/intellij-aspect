load("//testing/rules:defs.bzl", "test_config", "test_fixture", "test_matrix")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

test_config(
    name = "base",
    aspects = [
        "@intellij_aspect//modules:xcode_info.bzl%intellij_xcode_info_aspect",
        "@intellij_aspect//modules:cc_info.bzl%intellij_cc_info_aspect",
        "@intellij_aspect//intellij:aspect.bzl%intellij_info_aspect",
    ],
    bazel = "@registry_bazel//:8_5_1",
    modules = ["//testing/rules:aspect_module"],
)

test_matrix(
    name = "matrix",
    base = ":base",
    bazel = [
        "@registry_bazel//:7_7_0",
        "@registry_bazel//:8_5_1",
    ],
    modules = [
        "@registry_rules_cc//:0_1_1",
        "@registry_rules_cc//:0_2_9",
        "@registry_rules_cc//:0_2_14",
    ],
)

test_config(
    name = "bazel9",
    aspects = [
        "@intellij_aspect//modules:xcode_info.bzl%intellij_xcode_info_aspect",
        "@intellij_aspect//modules:cc_info.bzl%intellij_cc_info_aspect",
        "@intellij_aspect//intellij:aspect.bzl%intellij_info_aspect",
    ],
    bazel = "@registry_bazel//:9_0_0",
    modules = [
        "//testing/rules:aspect_module",
        "@registry_rules_cc//:0_2_14",
    ],
)

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    configs = [
        ":bazel9",
        ":matrix",
    ],
    targets = ["//:main"],
)

test_fixture(
    name = "transition",
    srcs = glob(["transition/**"]),
    configs = [":bazel9"],
    targets = [
        "//:main",
        "//:foo_bar",
    ],
)

test_fixture(
    name = "builtin",
    srcs = glob(["builtin/**"]),
    configs = [":matrix"],
    targets = ["//:main"],
)

test_fixture(
    name = "tool",
    srcs = glob(["tool/**"]),
    configs = [":bazel9"],
    targets = ["//:tool"],
)
