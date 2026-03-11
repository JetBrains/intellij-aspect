load("//common:common.bzl", "intellij_common")
load(":provider.bzl", "intellij_provider")

def _aspect_impl(target, ctx):
    if not ctx.rule.kind.endswith("_test"):
        return [intellij_provider.TestInfo(present = False)]

    return [intellij_provider.create(
        provider = intellij_provider.TestInfo,
        value = intellij_common.struct(
            size = ctx.rule.attr.size,
        ),
    )]

intellij_test_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.TestInfo],
)
