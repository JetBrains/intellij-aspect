load("//private/rules:aspect_gen.bzl", "aspect_gen_config")
load("//private/rules:aspect_lib.bzl", "aspect_lib")

aspect_gen_config(
    name = "aspect_config",
    out = "aspect.bzl",
    modules = [
        "//modules/default",
        "//modules/cc",
    ],
)

aspect_lib(
    name = "config",
    files = [":aspect_config"] + glob([
        "*.bzl",
        "*.tpl",
    ]),
    visibility = ["//:__pkg__"],
)
