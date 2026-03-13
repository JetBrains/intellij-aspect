load("//private/rules:bazel_binary.bzl", "BazelBinary")

TestConfig = provider(
    doc = "Single fixture configuration (Bazel, modules, aspects).",
    fields = {
        "bazel": "BazelBinary - Bazel binary used to build the fixture.",
        "modules": "map[str, str] - Map of BCR modules the fixture depends upon.",
        "aspects": "list[str] - Aspects enabled when building the fixture.",
        "aspect_deployment": "str - Aspect deployment option (bcr, materialized, builtin).",
    },
)

TestMatrix = provider(
    doc = "Collection of derived test configurations.",
    fields = {
        "configs": "list[TestConfig] - Materialized configurations to execute.",
        "bazel_binaries": "list[BazelBinary] - List of bazel binaries used by any configurations.",
    },
)

def serialize_test_config(config):
    """Returns a struct that can be encoded into the proto representation of a test config."""
    aspect_deployment_map = {
        "bcr": 0,
        "materialized": 1,
        "builtin": 2,
    }

    return struct(
        bazel = struct(version = config.bazel.version, executable = config.bazel.executable.path),
        modules = [
            struct(name = name, version = version)
            for (name, version) in config.modules.items()
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

    binaries = {
        provider.executable: provider
        for matrix in matrixes
        for provider in matrix.bazel_binaries
    }

    return TestMatrix(configs = configs, bazel_binaries = binaries.values())

def _test_matrix_impl(ctx):
    bazel_binaries = [it[BazelBinary] for it in ctx.attr.bazel]
    module_combinations = [{}]

    # calculate the cartesian product of all module combinations
    for name, versions in ctx.attr.modules.items():
        new_combinations = []
        for combo in module_combinations:
            for version in versions:
                # dicts are mutable, so just make a copy of the current combination before adding the next module
                new_combo = dict(combo)
                new_combo[name] = version
                new_combinations.append(new_combo)

        # update the main list to the newly expanded list
        module_combinations = new_combinations

    configs = [
        TestConfig(
            bazel = bazel,
            modules = modules,
            aspects = ctx.attr.aspects,
            aspect_deployment = ctx.attr.aspect_deployment,
        )
        for bazel in bazel_binaries
        for modules in module_combinations
    ]

    return [TestMatrix(configs = configs, bazel_binaries = bazel_binaries)]

test_matrix = rule(
    implementation = _test_matrix_impl,
    doc = "A single configuration that describes how a test fixture should be processed.",
    attrs = {
        "bazel": attr.label_list(
            mandatory = True,
            providers = [BazelBinary],
            doc = "bazel binary used to build the fixture, generates matrix over all provided versions",
        ),
        "modules": attr.string_list_dict(
            mandatory = True,
            doc = "map of BCR modules the fixture depends upon, generates matrix over all provided versions",
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
