load("@bazel_jar_jar//:jar_jar.bzl", "jar_jar")
load("@rules_java//java:defs.bzl", "java_binary")

# The full deploy jar, without the kotlin stdlib, before renaming any classes.
java_binary(
    name = "sdk_unshaded",
    create_executable = False,
    deploy_env = ["//third_party/kotlin:deploy_env"],
    runtime_deps = ["//sdk"],
)

# The sdk jar used to interact with the aspect. To avoid conflicts with other versions of protobuf,
# the protobuf infrastructure is renamed to be internal to this project, consumers are expected to provide
# the kotlin stdlib themselves on their runtime classpath.
jar_jar(
    name = "sdk",
    inline_rules = ["rule com.google.protobuf.** com.intellij.aspect.internal.protoinfra.@1"],
    input_jar = "//:sdk_unshaded_deploy.jar",
    output_jar = "sdk_deploy.jar",
    visibility = ["//visibility:public"],
)
