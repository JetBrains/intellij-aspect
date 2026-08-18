load("//private/rules:aspect_lib.bzl", "aspect_lib")
load("//modules:module.bzl", "intellij_module")

intellij_module.container(
    name = "module_container",
    visibility = ["//visibility:public"],
)

aspect_lib(
    name = "intellij",
    files = glob(["*.bzl"]),
    visibility = ["//:__pkg__"],
)
