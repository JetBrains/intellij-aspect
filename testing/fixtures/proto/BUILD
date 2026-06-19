load("//testing/rules:defs.bzl", "test_fixture")

package(
    default_testonly = 1,
    default_visibility = ["//testing/tests:__subpackages__"],
)

_MODULES = [
    "//testing/fixtures:rules_java_9_3_0",
    "//testing/fixtures:protobuf_34_1",
    "//testing/fixtures:toolchains_llvm_latest",
]

_RULES_PROTO_MODULES = [
    "//testing/fixtures:rules_java_9_3_0",
    "//testing/fixtures:rules_proto_7_1_0",
    "//testing/fixtures:toolchains_llvm_latest",
]

_ASPECTS = [
    "modules:protobuf_info.bzl%intellij_protobuf_info_aspect",
    "modules:proto_info.bzl%intellij_proto_info_aspect",
    "modules:java_info.bzl%intellij_java_info_aspect",
    # order is important, run jvm_info and java_common after all JVM-languages
    "modules:jvm_info.bzl%intellij_jvm_info_aspect",
    "modules:java_common_info.bzl%intellij_java_common_info_aspect",
    "intellij:aspect.bzl%intellij_info_aspect",
]

_RULES_PROTO_ASPECTCS = [
    "modules:rules_proto_info.bzl%intellij_rules_proto_info_aspect",
    "modules:proto_info.bzl%intellij_proto_info_aspect",
    "modules:java_info.bzl%intellij_java_info_aspect",
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
    bazel = ">=8",  # only very old versions of protobuf support bazel 7
    modules = _MODULES,
    output_groups = select(
        {
            "@bazel_tools//src/conditions:windows": [],
            "//conditions:default": _OUTPUT_GROUPS,
        },
    ),
    targets = [
        "//...",
    ],
)

test_fixture(
    name = "rules_proto_simple",
    srcs = glob(["rules_proto_simple/**"]),
    aspects = _RULES_PROTO_ASPECTCS,
    bazel = "8",  # rules_proto is not bazel-9 ready
    bcr = False,
    modules = _RULES_PROTO_MODULES,
    output_groups = select(
        {
            "@bazel_tools//src/conditions:windows": [],
            "//conditions:default": _OUTPUT_GROUPS,
        },
    ),
    targets = [
        "//...",
    ],
)
