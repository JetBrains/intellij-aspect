load("@rules_java//java:defs.bzl", "java_binary")

# kotlin runtime environment needs to be provided by the consumer
java_binary(
    name = "deploy_env",
    create_executable = False,
    visibility = ["//visibility:public"],
    runtime_deps = ["@rules_kotlin//kotlin/compiler:kotlin-stdlib"],
)
