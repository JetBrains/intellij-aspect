load("//intellij:testing.bzl", "intellij_aspect_test")
load("//testing/rules:defs.bzl", "test_runner")

test_runner(
    fixture = "//testing/fixtures/cpp:builtin",
    test = "BuiltinRulesTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/cpp:tool",
    test = "ToolDepTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/cpp:simple",
    test = "InfoTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/cpp:simple",
    test = "ToolchainTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/cpp:custom_toolchain",
    test = "CustomToolchainTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/cpp:simple",
    test = "TestInfoTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/cpp:layout",
    test = "LayoutTest.kt",
)

test_runner(
    fixture = "//testing/fixtures/cpp:protobuf",
    test = "ProtobufTest.kt",
)

intellij_aspect_test(
    name = "aspect_test",
    cc = [
        "@abseil-cpp//absl/algorithm:algorithm",
        "@abseil-cpp//absl/algorithm:container",
        "@abseil-cpp//absl/container:flat_hash_map",
        "@abseil-cpp//absl/meta:type_traits",
        "@abseil-cpp//absl/status:status",
        "@abseil-cpp//absl/strings:strings",
    ],
)
