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

load("@rules_kotlin//kotlin/internal:defs.bzl", "KtCompilerPluginInfo", "KtJvmInfo", "TOOLCHAIN_TYPE")
load("@rules_kotlin//kotlin/internal:opts.bzl", "JavacOptions", "KotlincOptions", "javac_options_to_flags", "kotlinc_options_to_flags")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load(":provider.bzl", "intellij_provider")

def _get_additional_javac_options(ctx):
    if TOOLCHAIN_TYPE not in ctx.toolchains:
        return []
    kotlin_toolchain = ctx.toolchains[TOOLCHAIN_TYPE]
    toolchain_javac_opts = kotlin_toolchain.javac_options
    javac_opts_target = getattr(ctx.rule.attr, "javac_opts", None)
    javac_opts = javac_opts_target[JavacOptions] if javac_opts_target and JavacOptions in javac_opts_target else toolchain_javac_opts

    return javac_options_to_flags(javac_opts)

def _get_kotlinc_options(ctx):
    if TOOLCHAIN_TYPE not in ctx.toolchains:
        return []
    kotlin_toolchain = ctx.toolchains[TOOLCHAIN_TYPE]
    toolchain_kotlinc_opts = kotlin_toolchain.kotlinc_options
    kotlinc_opts_target = getattr(ctx.rule.attr, "kotlinc_opts", None)
    kotlinc_opts = kotlinc_opts_target[KotlincOptions] if kotlinc_opts_target and KotlincOptions in kotlinc_opts_target else toolchain_kotlinc_opts

    # if not specifically set, the default value of "jvm_target" in kotlinc_opts is an empty string.
    if not getattr(kotlinc_opts, "jvm_target", None) and getattr(kotlin_toolchain, "jvm_target", ""):
        kotlinc_opts = intellij_common.struct_update(kotlinc_opts, jvm_target = getattr(kotlin_toolchain, "jvm_target"))
    return kotlinc_options_to_flags(kotlinc_opts)

def _source_jars(output):
    if hasattr(output, "source_jars"):
        source_jars = output.source_jars
        if type(source_jars) == "depset":
            return source_jars.to_list()
        else:
            # assuming it returns sequence type here
            return source_jars
    if hasattr(output, "source_jar") and output.source_jar != None:
        return [output.source_jar]
    return []

def _get_jvm_outputs(target):
    return [
        intellij_common.struct(
            binary_jars = [artifact_location.from_file(output.class_jar)] if output.class_jar else [],
            interface_jars = [artifact_location.from_file(output.ijar)] if output.ijar else [],
            source_jars = [artifact_location.from_file(f) for f in _source_jars(output)],
        )
        for output in target[KtJvmInfo].outputs.jars
    ]

def _extract_kt_compiler_plugin_option(option):
    if type(option) != "struct":
        return None

    plugin_id = getattr(option, "id", "")
    option_value = getattr(option, "value", "")
    if not plugin_id or not option_value:
        return None

    return intellij_common.struct(
        plugin_id = plugin_id,
        option_value = option_value,
    )

def _get_kotlin_plugins(ctx, dep_targets):
    # accumulate Kotlin compiler plugin info
    direct_plugins = getattr(ctx.rule.attr, "plugins", [])
    dep_plugins = []

    # exported_compiler_plugins is not transitive, so we only iterate over direct dependencies.
    # See https://github.com/bazelbuild/rules_kotlin/blob/f14d01ef5af3ad7ff0660ff671ca9b20c8a020d2/kotlin/internal/jvm/jvm.bzl#L268
    for dep in dep_targets:
        if KtJvmInfo in dep:
            exported_compiler_plugins = getattr(dep[KtJvmInfo], "exported_compiler_plugins", None)
            if exported_compiler_plugins:
                dep_plugins.append(exported_compiler_plugins)
    return depset(direct = direct_plugins, transitive = dep_plugins).to_list()

def _extract_kt_compiler_plugin_info(plugin):
    if KtCompilerPluginInfo not in plugin:
        return None

    compiler_plugin_info = plugin[KtCompilerPluginInfo]

    plugin_jars = [
        artifact_location.from_file(it)
        for it in compiler_plugin_info.classpath.to_list()
        if it != None
    ]

    raw_options = compiler_plugin_info.options
    kt_compiler_plugin_options = [
        option
        for option in [_extract_kt_compiler_plugin_option(raw_option) for raw_option in raw_options]
        if option != None
    ]

    return intellij_common.struct(
        plugin_jars = plugin_jars,
        kotlinc_plugin_options = kt_compiler_plugin_options,
    )

def _get_kotlin_stdlibs(ctx):
    if not TOOLCHAIN_TYPE in ctx.toolchains:
        return []

    kotlin_toolchain = ctx.toolchains[TOOLCHAIN_TYPE]
    if not hasattr(kotlin_toolchain, "jvm_stdlibs"):
        return []

    return [artifact_location.from_file(f) for f in kotlin_toolchain.jvm_stdlibs.compile_jars.to_list()]

def _get_associates(target, ctx):
    associates_labels = [str(associate.label) for associate in getattr(ctx.rule.attr, "associates", [])]
    direct_dep_targets_list = [
        intellij_common.attr_as_label_list(ctx, attr)
        for attr in ["deps", "jars", "associates"]
    ]
    direct_dep_targets = [
        target
        for target_list in direct_dep_targets_list
        for target in target_list
    ]
    additional_associates = []
    for dep in direct_dep_targets:
        if str(dep.label) in associates_labels:
            for provider in intellij_provider.JVM_MODULES:
                provider_value = intellij_provider.get(dep, provider)
                if provider_value:
                    additional_associates = additional_associates + [str(target.label) for target in getattr(provider_value.internal_value, "exports", [])]
    return associates_labels + additional_associates

def _get_generated_jars(target, ctx):
    if hasattr(target[KtJvmInfo], "annotation_processing") and target[KtJvmInfo].annotation_processing and target[KtJvmInfo].annotation_processing.enabled:
        class_jars = [jar for jar in target[KtJvmInfo].annotation_processing.class_jar if jar != None]
        source_jars = [jar for jar in target[KtJvmInfo].annotation_processing.source_jar if jar != None]
        if hasattr(target[KtJvmInfo], "additional_generated_source_jars"):
            source_jars = source_jars + [jar for jar in target[KtJvmInfo].additional_generated_source_jars]
        if hasattr(target[KtJvmInfo], "all_output_jars"):
            class_jars = class_jars + [jar for jar in target[KtJvmInfo].all_output_jars]
        return [
            struct(
                binary_jars = [artifact_location.from_file(jar) for jar in class_jars],
                source_jars = [artifact_location.from_file(jar) for jar in source_jars],
            ),
        ]
    return []

def _aspect_impl(target, ctx):
    if not KtJvmInfo in target:
        return [
            intellij_provider.KotlinInfo(present = False),
        ]

    dep_targets_list = [
        intellij_common.attr_as_label_list(ctx, attr)
        for attr in ["associates", "deps", "exports", "jars"]
    ] + [[t] for t in [intellij_common.attr_as_target(ctx, "_java_toolchain")] if t != None]
    dep_targets = [
        target
        for target_list in dep_targets_list
        for target in target_list
    ]
    plugins = _get_kotlin_plugins(ctx, dep_targets)

    return [
        intellij_provider.create(
            provider = intellij_provider.KotlinInfo,
            value = intellij_common.struct(
                language_version = getattr(target[KtJvmInfo], "language_version", None),
                api_version = getattr(target[KtJvmInfo], "language_version", None),  # API version currently not exposed
                associates = _get_associates(target, ctx),
                kotlinc_opts = _get_kotlinc_options(ctx),
                stdlibs = _get_kotlin_stdlibs(ctx),
                kotlinc_plugin_infos = [
                    info
                    for info in [_extract_kt_compiler_plugin_info(plugin) for plugin in plugins]
                    if info != None
                ],
                exported_compiler_plugin_targets_from_deps = [str(plugin.label) for plugin in plugins],
                module_name = getattr(target[KtJvmInfo], "module_name", None),
            ),
            internal_value = intellij_common.struct(
                java_common = intellij_common.struct(
                    jars = _get_jvm_outputs(target),
                    generated_jars = _get_generated_jars(target, ctx),
                    javac_opts = _get_additional_javac_options(ctx),
                ),
                exports = intellij_common.attr_as_label_list(ctx, "exports"),
            ),
        ),
    ]

intellij_kotlin_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.KotlinInfo],
    toolchains = [TOOLCHAIN_TYPE],
    required_aspect_providers = [[it] for it in intellij_provider.JVM_MODULES if it != intellij_provider.KotlinInfo],
)
