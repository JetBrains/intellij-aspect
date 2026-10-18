load("//testing/rules:defs.bzl", "junit_test")

junit_test(
    data = ["compile_commands.json"],
    test = "JsonScannerTest.kt",
    deps = ["//tools/ccdb:json_scanner"],
)
