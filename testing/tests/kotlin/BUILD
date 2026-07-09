load("//intellij:testing.bzl", "intellij_aspect_test")
load("//testing/rules:defs.bzl", "test_runner")

test_runner(
    fixture = "//testing/fixtures/kotlin:simple",
    test = "SimpleTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/kotlin:associates",
    test = "AssociatesTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/kotlin:resources",
    test = "ResourcesTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/kotlin:ksp",
    test = "KspTest.kt",
)

intellij_aspect_test(
    name = "aspect_test",
    kotlin = [
        "//sdk",
        "@maven//:org_jetbrains_kotlinx_kotlinx_coroutines_core",
    ],
)
