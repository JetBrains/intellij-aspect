# Copyright 2026 JetBrains s.r.o.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

load("//intellij:aspect.bzl", "intellij_configure_aspect")
load("//modules:cc_info.bzl", cc_info = "module")
load("//modules:cc_toolchain_info.bzl", cc_toolchain_info = "module")
load("//modules:go_info.bzl", go_info = "module")
load("//modules:java_common_info.bzl", java_common_info = "module")
load("//modules:java_info.bzl", java_info = "module")
load("//modules:java_toolchain_info.bzl", java_toolchain_info = "module")
load("//modules:jvm_info.bzl", jvm_info = "module")
load("//modules:kotlin_info.bzl", kotlin_info = "module")
load("//modules:proto_info.bzl", proto_info = "module")
load("//modules:protobuf_info.bzl", protobuf_info = "module")
load("//modules:py_info.bzl", py_info = "module")
load("//modules:python_info.bzl", python_info = "module")
load("//modules:run_info.bzl", run_info = "module")
load("//modules:scala_info.bzl", scala_info = "module")
load("//modules:test_info.bzl", test_info = "module")
load("//modules:xcode_info.bzl", xcode_info = "module")

MODULES = [
    run_info,
    test_info,
    xcode_info,
    cc_info,
    cc_toolchain_info,
    java_info,
    java_toolchain_info,
    java_common_info,
    jvm_info,
    kotlin_info,
    scala_info,
    go_info,
    py_info,
    python_info,
    protobuf_info,
    proto_info,
]

intellij_aspect = intellij_configure_aspect(
    modules = MODULES,
    container = "//:module_container",
)
