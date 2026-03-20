load("@rules_java//java:java_library.bzl", "java_library")

def lib_suite(name, srcs):
    for src in srcs:
        suffix = src.rfind(".")
        lib_name = src[:suffix]
        java_library(
            name = lib_name,
            srcs = [src],
        )
