load("//testing/rules:defs.bzl", "test_fixture", "test_matrix")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

test_matrix(
    name = "matrix",
    aspects = [
        "@intellij_aspect//modules:py_info.bzl%intellij_py_info_aspect",
        "@intellij_aspect//intellij:aspect.bzl%intellij_info_aspect",
    ],
    bazel = [
        "@bazel_versions//:7_7_0",
        "@bazel_versions//:8_5_1",
        "@bazel_versions//:9_0_0",
    ],
    modules = {
        "rules_python": [
            "1.4.0",
            "1.6.3",
            "1.7.0",
        ],
    },
)

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    config = ":matrix",
    export_cache = "repo_cache",
    targets = [
        "//:main",
        "//:test",
    ],
)

test_fixture(
    name = "args",
    srcs = glob(["args/**"]),
    config = ":matrix",
    import_cache = ":repo_cache",
    targets = ["//:all"],
)
