load("//testing/rules:defs.bzl", "heap_analysis")

TAGS = [
    "manual",  # reports are expensive to build, do not include them in the default build
    "exclusive",  # building projects is expensive, it does not make sense to build many in parallel
]

heap_analysis(
    name = "intellij",
    bazel_version = "9.2.0",
    languages = [
        "java",
        "kotlin",
    ],
    overlays = {"android": "@intellij_android//:project.zip"},
    project = "@intellij_community//:project.zip",
    tags = TAGS,
)
