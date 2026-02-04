load("//testing/rules:defs.bzl", "test_fixture", "test_matrix")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_ASPECTS = [
    "@intellij_aspect//modules:xcode_info.bzl%intellij_xcode_info_aspect",
    "@intellij_aspect//modules:cc_info.bzl%intellij_cc_info_aspect",
    "@intellij_aspect//intellij:aspect.bzl%intellij_info_aspect",
]

_MODULES = {"rules_cc": [
    "0.1.1",
    "0.2.9",
    "0.2.14",
]}

test_matrix(
    name = "matrix",
    aspects = _ASPECTS,
    bazel = [
        "@registry_bazel//:7_7_0",
        "@registry_bazel//:8_5_1",
        "@registry_bazel//:9_0_0",
    ],
    modules = _MODULES,
    visibility = ["//visibility:private"],
)

test_matrix(
    name = "bazel8",
    aspects = _ASPECTS,
    bazel = ["@registry_bazel//:8_5_1"],
    modules = _MODULES,
    visibility = ["//visibility:private"],
)

test_matrix(
    name = "bazel9",
    aspects = _ASPECTS,
    bazel = ["@registry_bazel//:9_0_0"],
    modules = _MODULES,
    visibility = ["//visibility:private"],
)

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    config = ":matrix",
    export_cache = "repo_cache",
    targets = ["//:main"],
)

test_fixture(
    name = "transition",
    srcs = glob(["transition/**"]),
    config = ":matrix",
    import_cache = ":repo_cache",
    targets = [
        "//:main",
        "//:foo_bar",
    ],
)

test_fixture(
    name = "builtin",
    srcs = glob(["builtin/**"]),
    config = ":bazel8",
    import_cache = ":repo_cache",
    targets = ["//:main"],
)

test_fixture(
    name = "tool",
    srcs = glob(["tool/**"]),
    config = ":bazel9",
    import_cache = ":repo_cache",
    targets = ["//:tool"],
)
