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
        "build --java_runtime_version=remotejdk_21 --java_language_version=21",
        "build --tool_java_runtime_version=remotejdk_21 --tool_java_language_version=21",
    ],
    module_name = "rules_java",
    versions = [
        "8.16.1",
        "9.3.0",
    ],
)

test_module_deps(
    flags = [
        "build --java_runtime_version=remotejdk_21 --java_language_version=21",
        "build --tool_java_runtime_version=remotejdk_21 --tool_java_language_version=21",
        # required for implicit dependency on rules go
        "common --incompatible_autoload_externally=+@rules_cc",
    ],
    module_name = "rules_kotlin",
    versions = [
        "2.2.0",
        "2.3.20",
    ],
)

test_module_deps(
    flags = [
        # required for implicit dependency on rules go
        "common --incompatible_autoload_externally=+@rules_cc",
    ],
    module_name = "protobuf",
    versions = [
        "34.1",
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

test_module_deps(
    flags = [
        # required for Bazel 9.1.0 compatability
        "common --incompatible_autoload_externally=+@rules_cc",
    ],
    module_name = "rules_go",
    versions = [
        "0.60.0",
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
