load("//intellij:modules.bzl", "MODULES")
load("//modules:cc_debug_info.bzl", cc_debug_info = "module")
load("//modules:go_debug_info.bzl", go_debug_info = "module")
load("//modules:platform_info.bzl", platform_info = "module")
load("//modules:rules_proto_info.bzl", rules_proto_info = "module")
load("//private/rules:aspect_config.bzl", "aspect_config")
load("//private/rules:aspect_lib.bzl", "aspect_lib")

aspect_lib(
    name = "config",
    files = [
        "config.bzl",
        "config.tpl",
        "extension.bzl",
        ":aspect_bcr",
        ":debug_aspect",
    ],
    visibility = ["//:__pkg__"],
)

# aspect shipped in the BCR archive, legacy rules_proto is not supported
aspect_config(
    name = "aspect_bcr",
    location = "aspect.bzl%intellij_aspect",
    modules = MODULES,
)

# aspect description bundled into the SDK jar, used for materialized deployments
aspect_config(
    name = "aspect_ide",
    location = "aspect.bzl%intellij_aspect",
    modules = MODULES + [rules_proto_info],
    visibility = ["//sdk/lib:__pkg__"],
)

# debug aspect for CC and Go targets, shipped in the BCR archive and bundled into the SDK jar
aspect_config(
    name = "debug_aspect",
    location = "debug_aspect.bzl%intellij_debug_aspect",
    modules = [
        cc_debug_info,
        go_debug_info,
        platform_info,
    ],
    visibility = ["//sdk/lib:__pkg__"],
)
