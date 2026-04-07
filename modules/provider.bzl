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

def _intellij_module_provider():
    return provider(
        doc = "Module-specific IntelliJ metadata for a single target.",
        fields = {
            "outputs": "dict[str, depset[File]] - Output groups produced by this module.",
            "dependencies": "dict[int, depset[Target]] - Direct dependencies grouped by dependency type.",
            "value": "struct - Module-specific value serializable to protobuf.",
            "present": "bool - Whether the provider is present on this target.",
            "toolchains": "list[ToolchainAspectProvider] - Toolchains used by the specified target.",
        },
    )

_IntelliJCcInfo = _intellij_module_provider()
_IntelliJPyInfo = _intellij_module_provider()
_IntelliJJavaCommonInfo = _intellij_module_provider()
_IntelliJJvmInfo = _intellij_module_provider()
_IntelliJJavaInfo = _intellij_module_provider()
_IntelliJTestInfo = _intellij_module_provider()

_MODULE_PROVIDERS = {
    "c_ide_info": _IntelliJCcInfo,
    "py_ide_info": _IntelliJPyInfo,
    "jvm_target_info": _IntelliJJvmInfo,
    "java_common": _IntelliJJavaCommonInfo,
    "java_provider": _IntelliJJavaInfo,
    "test_info": _IntelliJTestInfo,
}

# Modules implying that jvm_info should run on the respective targets to obtain
# additional information from rule attributes that are common to more than one JVM language.
_JVM_MODULES = [
    _IntelliJJavaInfo,
    # KotlinInfo,
    # ScalaInfo,
]

_IntelliJJavaCommonContributorJava = _intellij_module_provider()

# Internal providers that contribute to the `JavaCommon` message which collects the information for
# Java-like languages that comes from more than one provider. The contributors themselves are not serialized;
# they are not part of `_MODULE_PROVIDERS` which the main aspect uses to create the text proto file.
# Instead the `java_common` aspect computes for each field the sum of the values comming from the individual
# providers and add the consolidated information to the message to be serialized.
_JAVA_COMMON_CONTRIBUTORS = [
    _IntelliJJavaCommonContributorJava,
    # JavaCommonContributorKotlin,
    # JavaCommonContributorScala,
]

def _intellij_toolchain_provider():
    return provider(
        doc = "Toolchain-specific IntelliJ metadata for a single toolchain target.",
        fields = {
            "info_file": "File - The intellij-info.txt that describes the toolchain.",
            "owner": "Target - The target the produced this toolchain",
            "present": "bool - Whether the provider is present on this target.",
        },
    )

_IntelliJCcToolchainInfo = _intellij_toolchain_provider()
_IntelliJXcodeToolchainInfo = _intellij_toolchain_provider()
_IntelliJJavaToolchainInfo = _intellij_toolchain_provider()

_TOOLCHAIN_PROVIDERS = [
    _IntelliJCcToolchainInfo,
    _IntelliJXcodeToolchainInfo,
    _IntelliJJavaToolchainInfo,
]

def _has_module_provider(target):
    """Returns whether the target has any module provider."""
    for provider in _MODULE_PROVIDERS.values():
        if provider in target and target[provider].present:
            return True

    return False

def _get_provider_or_none(target, provider):
    """Gets the specified module or toolchain provider from the target."""
    if not provider in target:
        return None

    instance = target[provider]
    if not instance.present:
        return None

    return instance

def _create(provider, value, outputs = None, dependencies = None, toolchains = None):
    """Creates a new instance of a module provider."""
    return provider(
        present = True,
        value = value,
        outputs = outputs or {},
        dependencies = dependencies or {},
        toolchains = toolchains or [],
    )

def _create_toolchain(provider, info_file, owner):
    """Creates a new instance of a toolchain provider."""
    return provider(
        present = True,
        info_file = info_file,
        owner = owner,
    )

intellij_provider = struct(
    CcInfo = _IntelliJCcInfo,
    CcToolchainInfo = _IntelliJCcToolchainInfo,
    XcodeToolchainInfo = _IntelliJXcodeToolchainInfo,
    JvmInfo = _IntelliJJvmInfo,
    JavaInfo = _IntelliJJavaInfo,
    JavaCommonInfo = _IntelliJJavaCommonInfo,
    JavaCommonContributorJava = _IntelliJJavaCommonContributorJava,
    JavaToolchainInfo = _IntelliJJavaToolchainInfo,
    PyInfo = _IntelliJPyInfo,
    TestInfo = _IntelliJTestInfo,
    JVM_MODULES = _JVM_MODULES,
    JAVA_COMMON_CONTRIBUTORS = _JAVA_COMMON_CONTRIBUTORS,
    MODULE_MAP = _MODULE_PROVIDERS,
    TOOLCHAINS = _TOOLCHAIN_PROVIDERS,
    ALL = _MODULE_PROVIDERS.values() + _TOOLCHAIN_PROVIDERS,
    has_module = _has_module_provider,
    get = _get_provider_or_none,
    create = _create,
    create_toolchain = _create_toolchain,
)
