load("@rules_kotlin//kotlin:core.bzl", "define_kt_toolchain", "kt_kotlinc_options")

kt_kotlinc_options(
    name = "kotlinc_options",
    x_context_parameters = True,
)

define_kt_toolchain(
    name = "toolchain",
    api_version = "2.2",
    jvm_target = "21",
    kotlinc_options = ":kotlinc_options",
    language_version = "2.2",
)
