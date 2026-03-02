load("//testing/rules:defs.bzl", "test_matrix", "test_matrix_suite")

_ASPECTS = [
    "modules:xcode_info.bzl%intellij_xcode_info_aspect",
    "modules:cc_info.bzl%intellij_cc_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

_MODULES = {
    "rules_cc": [
        "0.1.1",
        "0.2.9",
        "0.2.14",
    ],
}

def test_matrix_cc(name, bazel, builtin = False):
    test_matrix(
        name = name + "_bcr",
        aspects = _ASPECTS,
        bazel = bazel,
        modules = _MODULES,
        aspect_deployment = "bcr",
        visibility = ["//visibility:private"],
    )

    test_matrix(
        name = name + "_materialized",
        aspects = _ASPECTS,
        bazel = bazel,
        modules = _MODULES,
        aspect_deployment = "materialized",
        visibility = ["//visibility:private"],
    )

    deps = [name + "_bcr", name + "_materialized"]

    if builtin:
        test_matrix(
            name = name + "_builtin",
            aspects = _ASPECTS,
            bazel = bazel,
            modules = _MODULES,
            aspect_deployment = "builtin",
            visibility = ["//visibility:private"],
        )
        deps.append(name + "_builtin")

    test_matrix_suite(
        name = name,
        deps = deps,
        visibility = ["//visibility:private"],
    )
