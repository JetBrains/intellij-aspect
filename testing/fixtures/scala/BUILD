load("//testing/rules:defs.bzl", "local_test_fixture", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_scala_7_1_6",
    "//testing/fixtures:hermetic_cc_toolchain_latest",
]

_ASPECTS = [
    "modules:scala_info.bzl%intellij_scala_info_aspect",
    # order is important, run jvm_info and java_common after all JVM-languages
    "modules:jvm_info.bzl%intellij_jvm_info_aspect",
    "modules:java_common_info.bzl%intellij_java_common_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    aspects = _ASPECTS,
    bazel = ">=9",
    modules = _MODULES,
    targets = [
        "//src/test/com/example/foo:test",
    ],
    use_msys2 = True,
)
