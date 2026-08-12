load("//modules:module.bzl", "intellij_module")

def _implementation(target, ctx, attrs):
    return None

empty_7 = intellij_module.rule(
    name = "empty_7",
    implementation = _implementation,
    field = "empty_7",
)
