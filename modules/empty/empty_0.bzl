load("//modules:module.bzl", "intellij_module")

def _implementation(target, ctx, attrs):
    return None

empty_0 = intellij_module.rule(
    name = "empty_0",
    implementation = _implementation,
    field = "empty_0",
)
