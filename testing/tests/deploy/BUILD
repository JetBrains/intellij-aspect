load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load("//testing/rules:defs.bzl", "junit_test")

junit_test(
    associates = ["//sdk/lib:deploy"],
    data = ["//:archive_ide"],
    env = {
        "ARCHIVE_IDE": "$(rlocationpaths //:archive_ide)",
    },
    test = "DeployTest.kt",
    deps = ["@rules_java//java/runfiles"],
)

junit_test(
    associates = ["//sdk/lib:deploy"],
    test = "DeployFileTest.kt",
)

junit_test(
    associates = ["//sdk/lib:deploy"],
    data = ["//:archive_ide"],
    env = {
        "ARCHIVE_IDE": "$(rlocationpaths //:archive_ide)",
    },
    test = "AspectDefinitionTest.kt",
    deps = ["@rules_java//java/runfiles"],
)
