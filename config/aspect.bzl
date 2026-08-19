load("//intellij:aspect.bzl", "intellij_configure_aspect")
load("//modules:cc_info.bzl", cc_info = "module")
load("//modules:cc_toolchain_info.bzl", cc_toolchain_info = "module")
load("//modules:go_info.bzl", go_info = "module")
load("//modules:java_common_info.bzl", java_common_info = "module")
load("//modules:java_info.bzl", java_info = "module")
load("//modules:java_toolchain_info.bzl", java_toolchain_info = "module")
load("//modules:jvm_info.bzl", jvm_info = "module")
load("//modules:kotlin_info.bzl", kotlin_info = "module")
load("//modules:run_info.bzl", run_info = "module")
load("//modules:test_info.bzl", test_info = "module")

MODULES = [
    run_info,
    test_info,
    cc_info,
    cc_toolchain_info,
    java_info,
    java_toolchain_info,
    java_common_info,
    jvm_info,
    kotlin_info,
    go_info,
]

intellij_aspect = intellij_configure_aspect(
    modules = MODULES,
    container = "//:module_container",
)
