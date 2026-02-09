load("//private/rules:bazel_binary.bzl", "BazelBinary")

TestConfig = provider(
    doc = "Single fixture configuration (Bazel, modules, aspects).",
    fields = {
        "bazel": "BazelBinary - Bazel binary used to build the fixture.",
        "modules": "map[str, str] - Map of BCR modules the fixture depends upon.",
        "aspects": "list[str] - Aspects enabled when building the fixture.",
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
    """Returns a struct that can be encoded into the proto represenation of a test config."""
    return struct(
        bazel = struct(version = config.bazel.version, executable = config.bazel.executable.path),
        modules = [
            struct(name = name, version = version)
            for (name, version) in config.modules.items()
        ],
        aspects = config.aspects,
    )

def serialize_test_matrix(matrix):
    """Returns a list of structs for each config in the matrix."""
    return [serialize_test_config(config) for config in matrix.configs]

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
        TestConfig(bazel = bazel, modules = modules, aspects = ctx.attr.aspects)
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
    },
    provides = [TestMatrix],
)
