load("//testing/rules:defs.bzl", "test_config", "test_fixture", "test_matrix")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

test_config(
    name = "base",
    aspects = [
        "@intellij_aspect//modules:py_info.bzl%intellij_py_info_aspect",
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
        "@registry_rules_python//:1_4_0",
        "@registry_rules_python//:1_6_3",
        "@registry_rules_python//:1_7_0",
    ],
)

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    configs = [":matrix"],
    targets = [
        "//:main",
        "//:test",
    ],
)

test_fixture(
    name = "args",
    srcs = glob(["args/**"]),
    configs = [":matrix"],
    targets = ["//:all"],
)
