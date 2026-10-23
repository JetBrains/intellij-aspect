load("//intellij:modules.bzl", "MODULES")
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
    ],
    visibility = ["//:__pkg__"],
)

# aspect shipped in the BCR archive, legacy rules_proto is not supported since
# it is not a dependency of MODULE.bazel.bcr
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
