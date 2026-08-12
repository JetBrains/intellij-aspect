load("//modules:module.bzl", "intellij_module")

def _implementation(target, ctx, attrs):
    return None

empty_5 = intellij_module.rule(
    name = "empty_5",
    implementation = _implementation,
    field = "empty_5",
)
