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

load("@rules_cc//cc:action_names.bzl", "ACTION_NAMES")
load("@rules_cc//cc:cc_toolchain_config_lib.bzl", "env_entry", "env_set", "feature", "tool_path")
load("@rules_cc//cc:defs.bzl", "cc_common")
load("@rules_cc//cc/toolchains:cc_toolchain_config_info.bzl", "CcToolchainConfigInfo")

_SDP_ENV_ACTIONS = [
    ACTION_NAMES.c_compile,
    ACTION_NAMES.cpp_compile,
    ACTION_NAMES.cpp_link_executable,
    ACTION_NAMES.cpp_link_dynamic_library,
    ACTION_NAMES.cpp_link_static_library,
]

def _impl(ctx):
    # Mirrors a QNX q++ toolchain: an always-on feature that exports the SDP
    # environment variables over every compile and link action via env_set
    sdp_env_feature = feature(
        name = "sdp_env",
        enabled = True,
        env_sets = [
            env_set(
                actions = _SDP_ENV_ACTIONS,
                env_entries = [
                    env_entry(
                        key = "QNX_HOST",
                        value = "/proc/self/cwd/external/qnx_sdp/host/linux/x86_64",
                    ),
                    env_entry(
                        key = "QNX_TARGET",
                        value = "/proc/self/cwd/external/qnx_sdp/target/qnx",
                    ),
                ],
            ),
        ],
    )

    # dummy tool paths
    tool_paths = [
        tool_path(name = "gcc", path = "/usr/bin/false"),
        tool_path(name = "ld", path = "/usr/bin/false"),
        tool_path(name = "ar", path = "/usr/bin/false"),
        tool_path(name = "cpp", path = "/usr/bin/false"),
        tool_path(name = "gcov", path = "/usr/bin/false"),
        tool_path(name = "nm", path = "/usr/bin/false"),
        tool_path(name = "objdump", path = "/usr/bin/false"),
        tool_path(name = "strip", path = "/usr/bin/false"),
    ]

    return cc_common.create_cc_toolchain_config_info(
        ctx = ctx,
        toolchain_identifier = "sdp-custom-toolchain",
        host_system_name = "local",
        target_system_name = "sdp-qnx",
        target_cpu = "sdp_cpu",
        target_libc = "unknown",
        compiler = "sdp-qcc",
        abi_version = "unknown",
        abi_libc_version = "unknown",
        features = [sdp_env_feature],
        tool_paths = tool_paths,
    )

cc_toolchain_config = rule(
    implementation = _impl,
    attrs = {},
    provides = [CcToolchainConfigInfo],
)
