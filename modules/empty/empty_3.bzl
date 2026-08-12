load("//modules:module.bzl", "intellij_module")

def _implementation(target, ctx, attrs):
    return None

empty_3 = intellij_module.rule(
    name = "empty_3",
    implementation = _implementation,
    field = "empty_3",
)
