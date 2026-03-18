load("//testing/rules:defs.bzl", "test_module_deps")

package(
    default_testonly = 1,
    default_visibility = ["//testing/fixtures:__subpackages__"],
)

test_module_deps(
    module_name = "rules_cc",
    versions = [
        "0.1.1",
        "0.2.9",
        "0.2.14",
    ],
)

test_module_deps(
    flags = [
        "build --java_runtime_version=remotejdk_21",
        "build --java_language_version=21",
    ],
    module_name = "rules_java",
    versions = [
        "8.16.1",
        "9.3.0",
    ],
)

test_module_deps(
    module_name = "rules_python",
    versions = [
        "1.4.0",
        "1.6.3",
        "1.7.0",
    ],
)

_TOOLCHAINS_LLVM_CONFIG = """
llvm = use_extension("@toolchains_llvm//toolchain/extensions:llvm.bzl", "llvm")
llvm.toolchain(llvm_version = "16.0.0")
use_repo(llvm, "llvm_toolchain")

register_toolchains("@llvm_toolchain//:all")
"""

test_module_deps(
    config = _TOOLCHAINS_LLVM_CONFIG,
    module_name = "toolchains_llvm",
    versions = ["1.6.0"],
)
