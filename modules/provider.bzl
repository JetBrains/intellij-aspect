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
_IntelliJJavaInfo = _intellij_module_provider()

_MODULE_PROVIDERS = {
    "c_ide_info": _IntelliJCcInfo,
    "py_ide_info": _IntelliJPyInfo,
    "java_ide_info": _IntelliJJavaInfo,
}

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
    JavaInfo = _IntelliJJavaInfo,
    JavaToolchainInfo = _IntelliJJavaToolchainInfo,
    PyInfo = _IntelliJPyInfo,
    MODULE_MAP = _MODULE_PROVIDERS,
    TOOLCHAINS = _TOOLCHAIN_PROVIDERS,
    ALL = _MODULE_PROVIDERS.values() + _TOOLCHAIN_PROVIDERS,
    has_module = _has_module_provider,
    get = _get_provider_or_none,
    create = _create,
    create_toolchain = _create_toolchain,
)
