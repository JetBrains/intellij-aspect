_IntelliJInfo = provider(
    doc = "Aggregation provider for IntelliJ aspect outputs and dependency edges.",
    fields = {
        "key": "TargetKey - The key to uniquly identify this target taking the configuration and all aspect ids into considadrtion.",
        "outputs": "dict[str, depset[File]|None] - Output groups emitted by this target (e.g., intellij-info).",
        "dependencies": "dict[int, depset[Target]|None] - Direct dependencies grouped by dependency type (see intellij_deps constants).",
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

# all providers in the order they should be executed, i.e. dependenceis need to be listed before dependeants
_ORDERED = [
    _IntelliJRunInfo,
    _IntelliJTestInfo,
    _IntelliJCcInfo,
]

intellij_provider = struct(
    IntelliJInfo = _IntelliJInfo,
    RunInfo = _IntelliJRunInfo,
    TestInfo = _IntelliJTestInfo,
    CcInfo = _IntelliJCcInfo,
    ORDERED = _ORDERED,
)
