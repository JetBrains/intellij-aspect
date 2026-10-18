load("@rules_java//java:defs.bzl", "java_library")
load("//private/rules:aspect_lib.bzl", "aspect_lib")
load(":ccdb.bzl", "ccdb")
load(":java_25_binary.bzl", "java_25_binary")

java_library(
    name = "json_scanner",
    srcs = ["JsonScanner.java"],
    visibility = ["//testing/tests/ccdb:__pkg__"],
)

java_25_binary(
    name = "generator",
    srcs = ["Generator.java"],
    main_class = "Generator",
    visibility = ["//visibility:public"],
    deps = [":json_scanner"],
)

aspect_lib(
    name = "ccdb",
    build = ":BUILD.bcr",
    files = glob([
        "*.bzl",
        "*.java",
    ]),
    visibility = ["//:__pkg__"],
)
