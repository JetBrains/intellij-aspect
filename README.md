# IntelliJ Aspect (Split Architecture)

A modular, non-templated Bazel aspect for IntelliJ IDE integration, designed as a
drop-in replacement for the current monolithic aspect. The key design change is splitting
language/toolchain logic into independent **module aspects** that each produce a dedicated
provider, while a thin **aggregator aspect** merges those providers and writes a single
textproto per target for IDE import.

This architecture significantly reduces templating (a major source of friction in the old
aspect). When deployed from the BCR no templating is needed at all; the materialized
fallback still requires rewriting load statements and generating a config file. The new
design also enables publishing to the **Bazel Central Registry (BCR)**.

## Project Structure

```
intellij/       Main aggregator aspect (intellij_info_aspect)
modules/        Language & toolchain module aspects (cc, java, python, ...)
common/         Shared utilities (dependencies, artifact locations, IDE info serialization)
config/         Configuration system (Bazel version detection via repository rule)
sdk/            Public API: Kotlin deploy helpers and protobuf definitions
testing/        Test infrastructure (fixtures, rules, workers)
tools/          CLI utilities (deploy, differ, format)
private/        Internal build rules and extensions (registry, bazelisk)
```

## Artifacts and Deployment

The top-level `BUILD` file defines four targets that package the aspect for different uses:

| Target | Format | Purpose |
|---|---|---|
| `archive_bcr` | tar.gz | Publication to the Bazel Central Registry |
| `archive_ide` | zip | Deployment from the IDE into a user's workspace |
| `local_deploy` | local registry | A minimal local BCR registry for development and testing |

These support three deployment modes:

**BCR** -- The aspect is fetched from the BCR as a regular `bazel_dep`. This is the
intended default. It requires the user to add
`bazel_dep(name = "intellij_aspect", version = "...")` to their `MODULE.bazel`.

**Materialized** -- The aspect sources are written directly into the workspace (like the
old aspect). This requires limited templating (rewriting load statements, generating a
config file) and serves as a transparent fallback when BCR is unavailable.

**Local Registry** -- A local registry and distdir are written into the workspace (e.g.
`.ijaspect/`), and `--registry` / `--distdir` flags are added to `.bazelrc`. This works
identically to BCR but without actually publishing, useful during development.

## SDK vs Aspect

The repository contains two Bazel modules:

- **`intellij_aspect_sdk`** (`MODULE.bazel`) -- The development module. It declares all
  build-time dependencies (rules_kotlin, rules_pkg, protobuf, maven artifacts, etc.) and
  extensions needed to build, test, and package the aspect. This is what you work with when
  developing.

- **`intellij_aspect`** (`MODULE.bazel.bcr`) -- The published module. It declares only the
  runtime rule-set dependencies needed by the aspect itself (rules_cc, rules_python,
  rules_java) with `max_compatibility_level` set high as high as possible to allow version 
  flexibility. This is what users depend on.

When building `archive_bcr`, `MODULE.bazel.bcr` is renamed to `MODULE.bazel` inside the
archive, so consumers see a clean `intellij_aspect` module.

## Module Aspects

Each language or toolchain gets its own aspect in `modules/`. There are two kinds:

**Target aspects** (e.g. `cc_info`, `java_info`, `py_info`) run on regular targets and
collect language-specific information from providers like `CcInfo`, `JavaInfo`, or `PyInfo`.
They advertise their output provider via `provides = [...]`, which lets the aggregator
aspect discover them without hardcoded dependencies. Modules can be toggled from the command
line.

**Toolchain aspects** (e.g. `cc_toolchain_info`, `java_toolchain_info`, `xcode_info`)
exist because starting with Bazel 8, toolchain dependencies use a specialized edge
(`toolchains_aspects`) that the aggregator aspect cannot traverse directly. Toolchain
aspects therefore write their own proto file instead of contributing to the aggregator.
Target aspects declare their toolchain dependencies via `requires = [...]` (mandatory) or
`required_aspect_providers` (optional, toggleable).

## Testing Infrastructure

Tests live under `testing/` and are built around a matrix-based fixture system.

### Key concepts

**Fixtures** (`testing/fixtures/`) are small Bazel projects (cpp/simple, java/simple, etc.)
that get built with the aspect. A fixture is declared with `test_fixture()`, which
specifies the project sources, module dependencies, aspects to apply, and targets to build.

**Test matrix** -- Each fixture is automatically tested across a cartesian product of:
- **Bazel versions**: 7.7.0, 8.6.0, 9.0.0 (configurable via version specs like `">=8"`)
- **Deployment modes**: `bcr`, `materialized`, and optionally `builtin`
- **Module versions**: different versions of rule sets (e.g. rules_cc 0.1.1 vs 0.2.14)

**Test runners** (`test_runner()`) are Kotlin JUnit tests that consume a fixture. The
fixture data (aspect output files) is made available via the `AspectFixture` JUnit rule.

**Workers** -- Fixture builds are executed by a multiplex Bazel worker
(`testing/rules/worker/`). The number of Bazel servers per version is configurable via the
`--//testing/rules:max_servers` build setting (set to 1 in `.bazelrc` by default), avoiding
the overhead of spinning up a new server for every test configuration.

### Example

```starlark
# testing/fixtures/cpp/BUILD

test_module_deps(module_name = "rules_cc", versions = ["0.1.1", "0.2.14"])

test_fixture(
    name = "simple",
    srcs = glob(["simple/**"]),
    modules = [":rules_cc_latest"],
    aspects = ["intellij:aspect.bzl%intellij_info_aspect"],
    targets = ["//:main"],
)
```

```starlark
# testing/tests/cpp/BUILD

test_runner(
    test = "SimpleTest.kt",
    fixture = "//testing/fixtures/cpp:simple",
)
```

### Running tests

```sh
bazel test //testing/tests/...
```
