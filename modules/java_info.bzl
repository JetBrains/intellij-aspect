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

IMPORT_RULE_KIND = ["java_import", "jvm_import", "kt_jvm_import"]

def _get_javacopts_from_context(ctx):
    javacopts_raw = getattr(ctx.rule.attr, "javacopts", [])
    if javacopts_raw == None:  # "javacopts" might exist in ctx.rule.attr as None
        javacopts_raw = []
    javacopts = expand_make_variables(ctx, True, javacopts_raw)
    add_exports = ["--add-exports=" + export + "=ALL-UNNAMED" for export in getattr(ctx.rule.attr, "add_exports", [])]
    add_opens = ["--add-opens=" + open + "=ALL-UNNAMED" for open in getattr(ctx.rule.attr, "add_opens", [])]
    return javacopts + add_exports + add_opens

def _get_javacopts(target, ctx):
    java_info = target[JavaInfo]
    compilation_info = getattr(java_info, "compilation_info", None)
    module_flags_info = getattr(java_info, "module_flags_info", None)
    if compilation_info != None and module_flags_info != None:
        javacopts = expand_make_variables(ctx, True, compilation_info.javac_options.to_list())

        # javacopts here contain --add-export flags already (https://github.com/bazelbuild/rules_java/blob/faaab4062f81deefaeef76dd21b2a5212432f8e3/java/private/java_common_internal.bzl#L159)
        add_opens = ["--add-opens=" + open + "=ALL-UNNAMED" for open in module_flags_info.add_opens.to_list()]
        return javacopts + add_opens
    return _get_javacopts_from_context(ctx)

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
            interface_jars = [artifact_location.from_file(output.compile_jar)] if output.compile_jar else [],
            source_jars = [artifact_location.from_file(f) for f in _source_jars(output)],
        )
        for output in target[JavaInfo].java_outputs
    ]

def _has_api_generating_plugins(target):
    return len(target[JavaInfo].api_generating_plugins.processor_classes.to_list()) > 0

def _get_generated_jars(target):
    return [
        struct(
            binary_jars = [artifact_location.from_file(output.generated_class_jar)],
            source_jars = [artifact_location.from_file(output.generated_source_jar)],
        )
        for output in target[JavaInfo].java_outputs
        if (output != None) and (output.generated_class_jar != None)
    ]

def _runtime_jars(target):
    compilation_info = getattr(target[JavaInfo], "compilation_info", None)
    if compilation_info:
        return compilation_info.runtime_classpath
    return getattr(target[JavaInfo], "transitive_runtime_jars", depset())

def _compile_jars(target):
    compilation_info = getattr(target[JavaInfo], "compilation_info", None)
    return compilation_info.compilation_classpath if compilation_info else depset()

def _get_outputs(target, ctx):
    resolve_files = []
    resolve_transitives = []
    for out in target[JavaInfo].java_outputs:
        if getattr(out, "compile_jar", None):
            resolve_files += [out.compile_jar]
        elif getattr(out, "ijar", None):
            resolve_files += [out.ijar]
        if getattr(out, "class_jar", None):
            resolve_files += [out.class_jar]
        if getattr(out, "source_jars", None):
            if type(out.source_jars) == "depset":
                resolve_transitives += [out.source_jars]
            else:
                resolve_files += out.source_jars
    if intellij_common.label_is_external(target.label) or (ctx.rule.kind in IMPORT_RULE_KIND):
        return {"intellij-sync-java": depset(resolve_files, transitive = resolve_transitives + [
            _runtime_jars(target),
            _compile_jars(target),
            target[JavaInfo].transitive_source_jars,
        ])}
    else:
        return {"intellij-build-java": depset(
            resolve_files + [jo.jdeps for jo in target[JavaInfo].java_outputs if jo.jdeps != None],
            transitive = resolve_transitives,
        )}

def _aspect_impl(target, ctx):
    if not JavaInfo in target:
        return [
            intellij_provider.JavaInfo(present = False),
        ]
    return [
        intellij_provider.create(
            provider = intellij_provider.JavaInfo,
            outputs = _get_outputs(target, ctx),
            value = intellij_common.struct(
                full_compile_jars = artifact_location.from_depset(target[JavaInfo].full_compile_jars),
                has_api_generating_plugins = _has_api_generating_plugins(target),
            ),
            dependencies = {
                intellij_deps.COMPILE_TIME: intellij_deps.collect(
                    ctx,
                    attributes = COMPILE_TIME_DEPS,
                    toolchain_types = [JAVA_TOOLCHAIN_TYPE],
                ),
            },
            toolchains = intellij_deps.find_toolchains(ctx, JAVA_TOOLCHAIN_TYPE),
            internal_value = intellij_common.struct(
                java_common = intellij_common.struct(
                    jars = _get_jvm_outputs(target),
                    generated_jars = _get_generated_jars(target),
                    jdeps = [artifact_location.from_file(jo.jdeps) for jo in target[JavaInfo].java_outputs if jo.jdeps != None],
                    javac_opts = _get_javacopts(target, ctx),
                    jvm_target = True,
                ),
                exports = intellij_common.attr_as_label_list(ctx, "exports"),
            ),
        ),
    ]

intellij_java_info_aspect = intellij_common.aspect(
    implementation = _aspect_impl,
    provides = [intellij_provider.JavaInfo],
    requires = [intellij_java_toolchain_info_aspect],
    toolchains_aspects = [str(JAVA_TOOLCHAIN_TYPE)],
)
