load("@bazel_skylib//rules:diff_test.bzl", "diff_test")
load("//testing/rules:defs.bzl", "junit_test")
load("//tools/apidump:defs.bzl", "java_api_dump")

junit_test(
    test = "ReadTargetTest.kt",
    deps = [
        "//sdk",
        "//sdk/proto:ide_info_java_proto",
        "@protobuf//:protobuf_java",
    ],
)

java_api_dump(
    name = "api_dump",
    exclude_packages = ["com.google.devtools.intellij"],
    deps = ["//sdk"],
)

diff_test(
    name = "api_test",
    failure_message = "\n".join([
        "The API of the sdk changed. Review the diff and update the fixture with:",
        "  bazel build //testing/tests/sdk:api_dump",
        "  install -m 644 bazel-bin/testing/tests/sdk/api_dump.txt testing/tests/sdk/api.txt",
    ]),
    file1 = "api.txt",
    file2 = ":api_dump",
)
