load("//modules:module.bzl", "intellij_module")

def _implementation(target, ctx, attrs):
    return None

empty_6 = intellij_module.rule(
    name = "empty_6",
    implementation = _implementation,
    field = "empty_6",
)
