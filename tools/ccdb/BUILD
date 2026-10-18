load("@rules_java//java:defs.bzl", "java_library")
load(":ccdb.bzl", "ccdb")
load(":java_25_binary.bzl", "java_25_binary")

java_library(
    name = "json",
    srcs = ["Json.java"],
)

java_25_binary(
    name = "generator",
    srcs = ["Generator.java"],
    main_class = "Generator",
    visibility = ["//visibility:public"],
    deps = [":json"],
)

ccdb(
    name = "abseil",
    deps = ["@abseil-cpp//absl/time"],
)
