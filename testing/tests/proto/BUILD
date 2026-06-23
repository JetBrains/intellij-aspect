load("//testing/rules:defs.bzl", "test_runner")

test_runner(
    fixture = "//testing/fixtures/proto:simple",
    test = "SimpleTest.kt",
)

# For rules_proto we should be able to get the very same information through
# the respective module.
test_runner(
    fixture = "//testing/fixtures/proto:rules_proto_simple",
    test = "SimpleTest.kt",
    test_name = "RulesProtoSimpleTest",
)
