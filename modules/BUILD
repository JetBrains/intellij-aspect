load("//private/rules:aspect_lib.bzl", "aspect_lib")

aspect_lib(
    name = "modules",
    files = [
        "//modules/cc:files",
        "//modules/default:files",
        "//modules/empty:files",
    ] + glob(["*.bzl"]),
    visibility = ["//:__pkg__"],
)
