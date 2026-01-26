load("//testing/rules:defs.bzl", "test_config", "test_fixture", "test_matrix")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

test_config(
    name = "base",
    aspects = [
        "@intellij_aspect//modules:java_info.bzl%intellij_java_info_aspect",
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
        "@registry_bazel//:9_0_0",
    ],
    modules = [
        "@registry_rules_java//:8_16_1",
        "@registry_rules_java//:9_3_0",
    ],
)

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    configs = [":matrix"],
    targets = ["//:main"],
)
