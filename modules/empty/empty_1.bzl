load("//modules:module.bzl", "intellij_module")

def _implementation(target, ctx, attrs):
    return None

empty_1 = intellij_module.rule(
    name = "empty_1",
    implementation = _implementation,
    field = "empty_1",
)
