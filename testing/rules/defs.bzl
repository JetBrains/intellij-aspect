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

load("@rules_java//java:defs.bzl", "java_test")
load("@rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")
load(":bazel.bzl", _resolve_bazel_spec = "resolve")
load(":config.bzl", _test_matrix = "test_matrix", _test_matrix_suite = "test_matrix_suite")
load(":fixture.bzl", _test_fixture = "test_fixture")
load(":local.bzl", _local_test_fixture = "local_test_fixture")
load(":module_dep.bzl", _test_module_dep = "test_module_dep")
load(":project.bzl", _project_archive = "project_archive")

test_matrix = _test_matrix
test_matrix_suite = _test_matrix_suite

local_test_fixture = _local_test_fixture

def test_module_deps(module_name, versions, **kwargs):
    """Declares Bazel module dependencies for use in test fixtures.

    Creates a test_module_dep target for each version with an auto-generated
    name derived from the module name and version (e.g., module_name="rules_cc",
    version="0.2.14" produces a target named ":rules_cc_0_2_14"). The generated
    target can then be referenced in the "modules" list of a test_fixture. The
    last specified version is aliased as latest.

    Args:
        module_name: The module name as used in bazel_dep().
        versions: List of version strings of the module.
        **kwargs: Additional arguments passed to each test_module_dep.
    """
    for version in versions:
        _test_module_dep(
            name = "%s_%s" % (module_name, version.replace(".", "_")),
            module_name = module_name,
            version = version,
            **kwargs
        )

    native.alias(
        name = "%s_latest" % module_name,
        actual = "%s_%s" % (module_name, versions[-1].replace(".", "_")),
    )

def test_fixture(
        name,
        srcs,
        modules,
        aspects,
        targets,
        output_groups = [],
        bazel = None,
        builtin = False,
        strip_prefix = "",
        use_msys2 = False,
        extra_flags = []):
    """Creates a test fixture with the result of the IntelliJ aspect applied to the project.

    Packages a small Bazel project, builds it with the aspect across multiple
    configurations (Bazel versions, module versions, deployment modes), and collects
    the resulting .intellij-info.txt files for test validation.

    Args:
        name: Name of the fixture target.
        srcs: Source files for the test project. Typically uses glob(["project_name/**"]).
        bazel: Bazel version spec. Can be None (all versions), an int (e.g., 8),
            a string expression (e.g., ">=8", ">7", "<10"), or a list of version strings.
        modules: Label list of test_module_dep targets the fixture depends upon.
        aspects: List of aspect strings to apply.
        targets: List of targets to build in the test project.
        builtin: If True, also tests the builtin aspect deployment mode.
        strip_prefix: Optional. Prefix to strip from source file paths when creating
            the project archive. Defaults to the fixture name if not specified.
        use_msys2: If True, the BAZEL_SH environment variable is forwarded from the host
            to the nested Bazel invocations.

    Example:
        test_module_dep(name = "rules_cc", version = "0.2.14")

        test_fixture(
            name = "simple",
            srcs = glob(["simple/**"]),
            bazel = ">=8",
            modules = [":rules_cc"],
            aspects = ["intellij:aspect.bzl%intellij_info_aspect"],
            targets = ["//:main"],
        )
    """
    bazel_versions = _resolve_bazel_spec(bazel)
    matrix_name = name + "_matrix"

    _test_matrix(
        name = matrix_name + "_bcr",
        aspects = aspects,
        bazel = bazel_versions,
        modules = modules,
        aspect_deployment = "bcr",
        visibility = ["//visibility:private"],
        testonly = 1,
    )

    _test_matrix(
        name = matrix_name + "_materialized",
        aspects = aspects,
        bazel = bazel_versions,
        modules = modules,
        aspect_deployment = "materialized",
        visibility = ["//visibility:private"],
        testonly = 1,
    )

    configs = [matrix_name + "_bcr", matrix_name + "_materialized"]

    if builtin:
        _test_matrix(
            name = matrix_name + "_builtin",
            aspects = aspects,
            bazel = bazel_versions,
            modules = modules,
            aspect_deployment = "builtin",
            visibility = ["//visibility:private"],
            testonly = 1,
        )
        configs.append(matrix_name + "_builtin")

    _test_matrix_suite(
        name = matrix_name,
        deps = configs,
        visibility = ["//visibility:private"],
        testonly = 1,
    )

    _project_archive(
        name = name + "_project",
        srcs = srcs,
        visibility = ["//visibility:private"],
        strip_prefix = strip_prefix or name,
        testonly = 1,
    )

    _test_fixture(
        name = name,
        configs = [matrix_name],
        project = name + "_project",
        testonly = 1,
        targets = targets,
        output_groups = output_groups,
        use_msys2 = use_msys2,
        extra_flags = extra_flags,
    )

def _derive_test_class(test):
    """
    Derives the full test_class path from the current package and naming
    convention. All tests need to follow the test package naming convention.
    """

    class_name = test.removesuffix(".kt")
    relative_path = native.package_name().replace("/", ".")

    return "com.intellij.aspect.%s.%s" % (relative_path, class_name)

def test_runner(test, fixture, deps = None, env = None):
    """
    Creates a test runner. Runs the test for iterations of the fixture. The
    fixture can be loaded and iterated in the test using the AspectFixture rule:

    @Rule
    @JvmField
    val aspect = AspectFixture()
    """
    name = test.removesuffix(".kt")

    kt_jvm_library(
        name = name + "_lib",
        srcs = [test],
        deps = (deps or []) + [
            "//private/lib:platform",
            "//testing/rules/fixture:fixture_lib",
            "//testing/tests/lib:utils",
            "//sdk",
            "@maven//:junit_junit",
            "@maven//:com_google_truth_truth",
        ],
        visibility = ["//visibility:private"],
        testonly = 1,
    )

    java_test(
        name = name,
        data = [fixture],
        runtime_deps = [name + "_lib"],
        test_class = _derive_test_class(test),
        env = (env or {}) | {
            "ASPECT_FIXTURES": "$(rlocationpaths %s)" % (fixture),
        },
    )

def junit_test(test, deps = None, **kwargs):
    """Creates a JUnit4 test. All JUnit dependencies are provided."""
    name = test.removesuffix(".kt")

    kt_jvm_library(
        name = name + "_lib",
        srcs = [test],
        deps = (deps or []) + [
            "@maven//:junit_junit",
            "@maven//:com_google_truth_truth",
        ],
        visibility = ["//visibility:private"],
        testonly = 1,
    )

    java_test(
        name = name,
        runtime_deps = [name + "_lib"],
        test_class = _derive_test_class(test),
        **kwargs
    )
