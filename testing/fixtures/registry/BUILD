load("//testing/rules:defs.bzl", "registry", "registry_module")

package(
    default_testonly = 1,
    default_visibility = ["//testing/fixtures:__subpackages__"],
)

registry_module(
    name = "cc_false_toolchain",
    srcs = glob(["cc_false_toolchain/**"]),
    config = "register_toolchains('@cc_false_toolchain//:toolchain')",
)

registry_module(
    name = "false_launcher",
    srcs = glob(["false_launcher/**"]),
    config = "register_toolchains('@false_launcher//:toolchain')",
)

registry(
    name = "registry",
    modules = [
        ":cc_false_toolchain",
        ":false_launcher",
    ],
)
