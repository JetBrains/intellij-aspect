load("//intellij:testing.bzl", "intellij_aspect_test")
load("//testing/rules:defs.bzl", "test_runner")

test_runner(
    fixture = "//testing/fixtures/java:simple",
    test = "SimpleTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/java:macro",
    test = "MacroTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/java:plugin",
    test = "PluginTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/java:forwardedArtifacts",
    test = "ForwardTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/java:resources",
    test = "ResourcesTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/java:top_level_toolchain",
    test = "SimpleTest.kt",
    test_name = "TopLevelToolchain",
)

intellij_aspect_test(
    name = "aspect_test",
    java = [
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_truth_truth",
    ],
)
