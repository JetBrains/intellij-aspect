load("//modules:module.bzl", "intellij_module")

def _implementation(target, ctx, attrs):
    return None

empty_2 = intellij_module.rule(
    name = "empty_2",
    implementation = _implementation,
    field = "empty_2",
)
