_IntelliJInfo = provider(
    doc = "Aggregation provider for IntelliJ aspect outputs and dependency edges.",
    fields = {
        "key": "TargetKey - The key to uniquly identify this target taking the configuration and all aspect ids into considadrtion.",
        "outputs": "dict[str, depset[File]|None] - Output groups emitted by this target (e.g., intellij-info).",
        "dependencies": "dict[int, depset[Target]|None] - Direct dependencies grouped by dependency type (see intellij_deps constants).",
        "results": "dict[Provider, struct] - Results of modules executed on this target.",
    },
)

def _create_module_provider():
    return provider(
        doc = "A provider describing an IntelliJ aspect module.",
        fields = {
            "field": "str|None - Field of intellij-info.txt the value is written to. None for modules that only feed other modules.",
            "func": "function - func(target, ctx, attrs) -> struct|None, called for every visited target.",
        },
    )

_IntelliJRunInfo = _create_module_provider()
_IntelliJTestInfo = _create_module_provider()
_IntelliJCcInfo = _create_module_provider()
_IntelliJCcToolchainInfo = _create_module_provider()
_IntelliJJavaInfo = _create_module_provider()
_IntelliJJavaToolchainInfo = _create_module_provider()
_IntelliJJavaCommonInfo = _create_module_provider()
_IntelliJJvmInfo = _create_module_provider()
_IntelliJKotlinInfo = _create_module_provider()
_IntelliJGoInfo = _create_module_provider()
_IntelliJPyInfo = _create_module_provider()  # used by CLwB
_IntelliJPythonInfo = _create_module_provider()  # used by the JB plugin

# all providers in the order they should be executed, i.e. dependencies need to be listed before dependeants
_ORDERED = [
    _IntelliJRunInfo,
    _IntelliJTestInfo,
    _IntelliJCcToolchainInfo,
    _IntelliJCcInfo,
    _IntelliJJavaToolchainInfo,
    _IntelliJJavaInfo,
    _IntelliJKotlinInfo,
    _IntelliJJavaCommonInfo,
    _IntelliJJvmInfo,
    _IntelliJGoInfo,
    _IntelliJPyInfo,
    _IntelliJPythonInfo,
]

# Modules implying that jvm_info should run on the respective targets to obtain
# additional information from rule attributes that are common to more than one JVM language.
# Also used by java_common to collect information contributed to by more than one provider.
_JVM = [
    _IntelliJJavaInfo,
    _IntelliJKotlinInfo,
]

intellij_provider = struct(
    IntelliJInfo = _IntelliJInfo,
    RunInfo = _IntelliJRunInfo,
    TestInfo = _IntelliJTestInfo,
    CcInfo = _IntelliJCcInfo,
    CcToolchainInfo = _IntelliJCcToolchainInfo,
    JavaInfo = _IntelliJJavaInfo,
    JavaToolchainInfo = _IntelliJJavaToolchainInfo,
    JavaCommonInfo = _IntelliJJavaCommonInfo,
    JvmInfo = _IntelliJJvmInfo,
    KotlinInfo = _IntelliJKotlinInfo,
    GoInfo = _IntelliJGoInfo,
    PyInfo = _IntelliJPyInfo,
    PythonInfo = _IntelliJPythonInfo,
    ORDERED = _ORDERED,
    JVM = _JVM,
)
