load("@rules_java//java:java_binary.bzl", "java_binary")
load("@with_cfg.bzl", "with_cfg")

java_25_binary, _ = with_cfg(java_binary) \
    .set("java_language_version", "25") \
    .set("java_runtime_version", "remotejdk_25") \
    .build()
