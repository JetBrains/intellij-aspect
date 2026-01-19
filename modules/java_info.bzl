load("@rules_java//java:defs.bzl", "JavaInfo")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:dependencies.bzl", "intellij_deps")
load("//common:make_variables.bzl", "expand_make_variables")
load(":java_toolchain_info.bzl", "JAVA_TOOLCHAIN_TYPE", "intellij_java_toolchain_info_aspect")
load(":provider.bzl", "intellij_provider")

COMPILE_TIME_DEPS = [
    "jars",
    "_java_toolchain",
    "_jvm",
    "runtime_jdk",
]

def _get_jvm_outputs(target, ctx):
    if hasattr(target[JavaInfo], "java_outputs"):
        jars = target[JavaInfo].java_outputs
    elif hasattr(target[JavaInfo], "outputs"):
        jars = target[JavaInfo].outputs.jars
    else:
        return None
    source_jars_entries = [
        entry.source_jars
        for entry in jars
        if (hasattr(entry, "source_jars") and entry.source_jars)
    ]
    return intellij_common.struct(
        binary_jars = [
            artifact_location.from_file(jar.class_jar)
            for jar in jars
            if (hasattr(jar, "class_jar") and jar.class_jar)
        ],
        interface_jars = [
            artifact_location.from_file(jar.compile_jar)
            for jar in jars
            if (hasattr(jar, "compile_jar") and jar.compile_jar)
        ],
        source_jars = [
            artifact_location.from_file(jar)
            for entry in source_jars_entries
            for jar in entry.to_list()
        ] or [
            artifact_location.from_file(jar.source_jar)
            for jar in jars
            if (hasattr(jar, "source_jar") and jar.source_jar)
        ],
        jdeps = [
            artifact_location.from_file(jar.jdeps)
            for jar in jars
            if (hasattr(jar, "jdeps") and jar.jdeps)
        ],
    )

def _has_api_generating_plugins(target, ctx):
    return len(target[JavaInfo].api_generating_plugins.processor_classes.to_list()) > 0

def _get_jvm_info(target, ctx):
    return intellij_common.struct(
        args = intellij_common.attr_as_list(ctx, "args"),
        main_class = getattr(ctx.rule.attr, "main_class", None),
        javac_opts = expand_make_variables(ctx, True, intellij_common.attr_as_list(ctx, "javacopts")),
        jvm_flags = expand_make_variables(ctx, True, intellij_common.attr_as_list(ctx, "jvm_flags")),
        jars = _get_jvm_outputs(target, ctx),
        has_api_generating_plugins = _has_api_generating_plugins(target, ctx),
    )

def _aspect_impl(target, ctx):
    if not JavaInfo in target:
        return [intellij_provider.JavaInfo(present = False)]
    all_sources = artifact_location.from_attr(ctx, "srcs")
    return [intellij_provider.create(
        provider = intellij_provider.JavaInfo,
        value = intellij_common.struct(
            sources = [s for s in all_sources if s.is_source],
            generated_sources = [s for s in all_sources if not s.is_source],
            jvm_target_info = _get_jvm_info(target, ctx),
        ),
        dependencies = {
            intellij_deps.COMPILE_TIME: intellij_deps.collect(
                ctx,
                attributes = COMPILE_TIME_DEPS,
                toolchain_types = [JAVA_TOOLCHAIN_TYPE],
            ),
        },
        toolchains = intellij_deps.find_toolchains(ctx, JAVA_TOOLCHAIN_TYPE),
    )]

intellij_java_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.JavaInfo],
    requires = [intellij_java_toolchain_info_aspect],
    toolchains_aspects = [str(JAVA_TOOLCHAIN_TYPE)],
)
