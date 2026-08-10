_IntelliJModule = provider(
    doc = "A language or toolchain module of the IntelliJ aspect.",
    fields = {
        "name": "str - Identity of the module. Used to look up its result from other modules.",
        "field": "str|None - Field of intellij-info.txt the value is written to. None for modules that only feed other modules.",
        "impl": "function - impl(target, ctx, attrs) -> struct|None, called for every visited target.",
        "attr": "dict - Collection of module attributes.",
        "deps": "depset[_IntelliJModule] - All modules that are required by this module."
    },
)

_IntelliJInfo = provider(
    doc = "Aggregation provider for IntelliJ aspect outputs and dependency edges.",
    fields = {
        "key": "TargetKey - The key to uniquly identify this target taking the configuration and all aspect ids into considadrtion.",
        "outputs": "dict[str, depset[File]|None] - Output groups emitted by this target (e.g., intellij-info).",
        "dependencies": "dict[int, depset[Target]|None] - Direct dependencies grouped by dependency type (see intellij_deps constants).",
        "modules": "dict[_IntelliJModule, struct] - Output of all executed modules.",
        "owner": "Target - The target associated with this provider.",
    },
)

intellij_provider = struct(
    Module = _IntelliJModule,
    Info = _IntelliJInfo,
)
