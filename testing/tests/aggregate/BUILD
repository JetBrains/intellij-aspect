load("//intellij:aggregate.bzl", "intellij_aspect_aggregate")
load(":defs.bzl", "aggregate_output_groups_nonempty_test", "aggregate_output_groups_test", "fake_output_groups")

package(default_testonly = 1)

fake_output_groups(name = "left")

fake_output_groups(name = "right")

intellij_aspect_aggregate(
    name = "aggregate",
    deps = [
        ":left",
        ":right",
    ],
)

aggregate_output_groups_test(
    name = "AggregateOutputGroupsTest",
    expected_roots = [
        "left",
        "right",
    ],
    target = ":aggregate",
)

intellij_aspect_aggregate(
    name = "aspect_aggregate",
    deps = ["@maven//:com_google_guava_guava"],
)

aggregate_output_groups_nonempty_test(
    name = "AggregateAspectOutputsTest",
    groups = [
        "intellij-info",
        "intellij-build",
    ],
    target = ":aspect_aggregate",
)
