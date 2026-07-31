load("//common:common.bzl", "intellij_common")
load(":provider.bzl", "intellij_provider")

def _empty_aspect(provider):
    def impl(ctx, target):
        return [provider(present = False)]
    

    return intellij_common.aspect(
        implementation = impl,
        provides = [provider],
    )

intellij_empty_0_aspect = _empty_aspect(intellij_provider.Empty0Info)
intellij_empty_1_aspect = _empty_aspect(intellij_provider.Empty1Info)
intellij_empty_2_aspect = _empty_aspect(intellij_provider.Empty2Info)
intellij_empty_3_aspect = _empty_aspect(intellij_provider.Empty3Info)
intellij_empty_4_aspect = _empty_aspect(intellij_provider.Empty4Info)
intellij_empty_5_aspect = _empty_aspect(intellij_provider.Empty5Info)
intellij_empty_6_aspect = _empty_aspect(intellij_provider.Empty6Info)
intellij_empty_7_aspect = _empty_aspect(intellij_provider.Empty7Info)
intellij_empty_8_aspect = _empty_aspect(intellij_provider.Empty8Info)
lntellij_empty_9_aspect = _empty_aspect(intellij_provider.Empty9Info)
