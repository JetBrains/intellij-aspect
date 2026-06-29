load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//testing/rules:defs.bzl", "junit_test")

junit_test(
    data = ["//:archive_ide"],
    env = {
        "ARCHIVE_IDE": "$(rlocationpaths //:archive_ide)",
    },
    test = "DeployTest.kt",
    deps = [
        "//sdk/lib:deploy",
        "@rules_java//java/runfiles",
    ],
)

junit_test(
    test = "DeployFileTest.kt",
    deps = [
        "//sdk/lib:deploy",
    ],
)
