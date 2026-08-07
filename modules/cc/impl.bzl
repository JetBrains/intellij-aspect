load("//modules:module.bzl", "intellij_module")

def _implementation(target, ctx, attrs):
    return None

module = intellij_module.rule(
    name = "cc",
    implementation = _implementation,
    field = "c_ide_info",
    attrs = {"_toolchain": attr.label(default = Label("@rules_cc//cc:optional_current_cc_toolchain"))},
)
