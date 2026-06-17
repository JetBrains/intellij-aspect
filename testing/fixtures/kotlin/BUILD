load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_kotlin_2_3_20",
    "//testing/fixtures:toolchains_llvm_latest",
] + select({
    # rules_kotlin 2.2.x can fail on Windows with: LAUNCHER ERROR: Rlocation failed on ... annotations-13.0.jar
    # https://github.com/bazel-contrib/rules_kotlin/issues/1300
    "@bazel_tools//src/conditions:windows": [],
    "//conditions:default": ["//testing/fixtures:rules_kotlin_2_2_0"],
})

_MOUDLES_WITH_JAVA = _MODULES + [
    "//testing/fixtures:rules_java_9_3_0",
]

_ASPECTS = [
    "modules:kotlin_info.bzl%intellij_kotlin_info_aspect",
    # order is important, run jvm_info and java_common after all JVM-languages
    "modules:jvm_info.bzl%intellij_jvm_info_aspect",
    "modules:java_common_info.bzl%intellij_java_common_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

_ASPECTS_WITH_JAVA = [
    "modules:java_info.bzl%intellij_java_info_aspect",
    "modules:kotlin_info.bzl%intellij_kotlin_info_aspect",
    # order is important, run jvm_info and java_common after all JVM-languages
    "modules:jvm_info.bzl%intellij_jvm_info_aspect",
    "modules:java_common_info.bzl%intellij_java_common_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

_OUTPUT_GROUPS = [
    "intellij-sync",
    "intellij-build",
]

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    output_groups = _OUTPUT_GROUPS,
    targets = [
        "//:main",
    ],
)

test_fixture(
    name = "associates",
    srcs = glob(["associates/**"]),
    aspects = _ASPECTS,
    modules = _MODULES,
    targets = [
        "//:A",
    ],
)

test_fixture(
    name = "resources",
    srcs = glob(["resources/**"]),
    aspects = _ASPECTS_WITH_JAVA,
    modules = _MOUDLES_WITH_JAVA,
    targets = [
        "//module:a-lib",
    ],
)
