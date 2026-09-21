load("//testing/rules:defs.bzl", "heap_analysis")

TAGS = [
    "manual",  # reports are expensive to build, do not include them in the default build
    "exclusive",  # building projects is expensive, it does not make sense to build many in parallel
]

USE_CASES = [
    ("", False, False),
    ("-nosync", True, False),
    ("-plain", True, True),
]

[
    heap_analysis(
        name = "intellij" + extension,
        bazel_version = "9.2.0",
        languages = [
            "java",
            "kotlin",
        ],
        nobuildgroup = nobuild,
        nosyncgroup = nosync,
        overlays = {"android": "@intellij_android//:project.zip"},
        project = "@intellij_community//:project.zip",
        tags = TAGS,
    )
    for (extension, nosync, nobuild) in USE_CASES
]

[
    heap_analysis(
        name = "pigweed" + extension,
        bazel_version = "8.7.0",
        extra_flags = ["--workspace_status_command="],
        languages = [
            "cc",
            "python",
        ],
        nobuildgroup = nobuild,
        nosyncgroup = nosync,
        project = "@google_pigweed//:project.zip",
        tags = TAGS,
        targets = [
            "//...",
            "-//pw_build_info/...",
        ],
    )
    for (extension, nosync, nobuild) in USE_CASES
]

[
    heap_analysis(
        name = "bazel" + extension,
        bazel_version = "9.2.0",
        languages = [
            "cc",
            "python",
            "java",
        ],
        nobuildgroup = nobuild,
        nosyncgroup = nosync,
        project = "@bazelbuild_bazel//:project.zip",
        tags = TAGS,
    )
    for (extension, nosync, nobuild) in USE_CASES
]
