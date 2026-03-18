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

load(":module_dep.bzl", "TestModuleDep")

TestConfig = provider(
    doc = "Single fixture configuration (Bazel version, modules, aspects).",
    fields = {
        "bazel": "str - Bazel version string (e.g., 8.6.0).",
        "modules": "list[TestModuleDep] - List of BCR modules the fixture depends upon.",
        "aspects": "list[str] - Aspects enabled when building the fixture.",
        "aspect_deployment": "str - Aspect deployment option (bcr, materialized, builtin).",
    },
)

TestMatrix = provider(
    doc = "Collection of derived test configurations.",
    fields = {
        "configs": "list[TestConfig] - Materialized configurations to execute.",
    },
)

def config_hash(config):
    """
    Generates a unique hash for the configuration. Used to generate unique file
    names for every fixture.
    """
    parts = [config.bazel]
    parts.extend(["%s:%s" % (m.name, m.version) for m in config.modules])
    parts.append(config.aspect_deployment)
    parts.extend(config.aspects)

    return hash(".".join(parts))

def config_name(config):
    """
    A user friendly name for the configuration, including bazel and module
    versions.
    """
    parts = ["bazel:%s" % config.bazel, "deploy:%s" % config.aspect_deployment] + [
        "%s:%s" % (m.name, m.version)
        for m in config.modules
    ]

    return "[%s]" % ", ".join(parts)

def serialize_test_config(config):
    """Returns a struct that can be encoded into the proto representation of a test config."""
    aspect_deployment_map = {
        "bcr": 0,
        "materialized": 1,
        "builtin": 2,
    }

    return struct(
        bazel_version = config.bazel,
        modules = [
            struct(name = it.name, version = it.version, config = it.config, flags = it.flags)
            for it in config.modules
        ],
        aspects = config.aspects,
        aspect_deployment = aspect_deployment_map[config.aspect_deployment],
    )

def merge_matrixes(matrixes):
    configs = [
        config
        for matrix in matrixes
        for config in matrix.configs
    ]

    return TestMatrix(configs = configs)

def _test_matrix_impl(ctx):
    deps = [it[TestModuleDep] for it in ctx.attr.modules]

    # group TestModuleDep targets by module name
    groups = {}
    for dep in deps:
        if dep.name not in groups:
            groups[dep.name] = []
        groups[dep.name].append(dep)

    # calculate the cartesian product of all module combinations
    module_combinations = [[]]
    for name, dep_list in groups.items():
        new_combinations = []
        for combo in module_combinations:
            for dep in dep_list:
                new_combinations.append(combo + [dep])

        module_combinations = new_combinations

    configs = [
        TestConfig(
            bazel = version,
            modules = modules,
            aspects = ctx.attr.aspects,
            aspect_deployment = ctx.attr.aspect_deployment,
        )
        for version in ctx.attr.bazel
        for modules in module_combinations
    ]

    return [TestMatrix(configs = configs)]

test_matrix = rule(
    implementation = _test_matrix_impl,
    doc = "Generates a test matrix from the cartesian product of Bazel versions, module versions, and a deployment mode.",
    attrs = {
        "bazel": attr.string_list(
            mandatory = True,
            doc = "bazel version strings to test (e.g., ['8.6.0', '9.0.0'])",
        ),
        "modules": attr.label_list(
            mandatory = True,
            providers = [TestModuleDep],
            doc = "list of TestModuleDep targets the fixture depends upon, generates matrix over all provided versions",
        ),
        "aspects": attr.string_list(
            mandatory = True,
            doc = "list of enabled aspects when building the fixture",
        ),
        "aspect_deployment": attr.string(
            default = "bcr",
            values = ["bcr", "materialized", "builtin"],
            doc = "aspect deployment option: bcr (default), materialized, or builtin",
        ),
    },
    provides = [TestMatrix],
)

def _test_matrix_suite_impl(ctx):
    return [merge_matrixes([it[TestMatrix] for it in ctx.attr.deps])]

test_matrix_suite = rule(
    implementation = _test_matrix_suite_impl,
    doc = "Merges multiple test matrices into a single matrix.",
    attrs = {
        "deps": attr.label_list(
            mandatory = True,
            providers = [TestMatrix],
            doc = "list of test_matrix targets to merge",
        ),
    },
    provides = [TestMatrix],
)
