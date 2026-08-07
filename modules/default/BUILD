load("//modules:module.bzl", "intellij_module")
load(":run_module.bzl", "run_module")
load(":test_module.bzl", "test_module")

intellij_module.declare(
    name = "run",
    rule = run_module,
)

intellij_module.declare(
    name = "test",
    rule = test_module,
)

intellij_module.group(
    name = "default",
    deps = [
        ":run",
        ":test",
    ],
)

intellij_module.filegroup()
