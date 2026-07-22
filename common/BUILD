load("//private/rules:aspect_lib.bzl", "aspect_lib")

aspect_lib(
    name = "common",
    files = glob([
        "*.bzl",
        "third_party/*.bzl",
    ]),
    visibility = ["//:__pkg__"],
)
