load("//private/rules:aspect_lib.bzl", "aspect_lib")
load(":aspect.bzl", "aspect_config")

aspect_config(
    name = "aspect",
    modules = [
        "//modules/default",
        "//modules/cc",
    ],
)

aspect_lib(
    name = "config",
    files = [
        "config.bzl",
        "config.tpl",
        "extension.bzl",
        ":aspect",
    ],
    visibility = ["//:__pkg__"],
)
