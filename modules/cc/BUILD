load("//modules:module.bzl", "intellij_module")
load(":module.bzl", "module")
load(":toolchain_module.bzl", "toolchain_module")

intellij_module.declare(
    name = "toolchain",
    rule = toolchain_module,
)

intellij_module.declare(
    name = "info",
    rule = module,
)

intellij_module.group(
    name = "cc",
    deps = [
        ":info",
        ":toolchain",
    ],
)

intellij_module.filegroup()
