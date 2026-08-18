load("//intellij:aspect.bzl", "intellij_configure_aspect")
load("//modules:run_info.bzl", run_info = "module")
load("//modules:test_info.bzl", test_info = "module")
load("//modules:cc_info.bzl", cc_info = "module")
load("//modules:cc_toolchain_info.bzl", cc_toolchain_info = "module")

intellij_aspect = intellij_configure_aspect([run_info, test_info, cc_info, cc_toolchain_info])
