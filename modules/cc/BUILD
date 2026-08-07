load("@rules_cc//cc:find_cc_toolchain.bzl", "CC_TOOLCHAIN_TYPE")
load("//modules:module.bzl", "intellij_module")
load(":impl.bzl", "module")

cc = intellij_module.declare(
    name = "cc",
    fragments = ["cpp"],
    rule = module,
    toolchains = [CC_TOOLCHAIN_TYPE],
)
