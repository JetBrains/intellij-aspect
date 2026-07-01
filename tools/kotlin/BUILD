load("@rules_kotlin//kotlin:core.bzl", "define_kt_toolchain")

# Overrides rules_kotlin's default toolchain, which pins language/api version to
# "2.1" while bundling a Kotlin 2.2.x compiler that warns 2.1 is deprecated.
define_kt_toolchain(
    name = "kotlin_toolchain",
    api_version = "2.2",
    jvm_target = "21",
    language_version = "2.2",
)
