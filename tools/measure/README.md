# `measure` - aspect analysis benchmark tool

Measures the cost of running the IntelliJ aspect over a Bazel project. It deploys the current
aspect into the project, then measures a baseline build and an aspect-enabled build repeatedly,
recording heap and GC statistics so the aspect's overhead can be read directly from the delta.

## What it measures

The tool measures against a fresh output base in a temporary directory, so it never touches a
server or cache of the project itself. It first pre-warms that output base with
`bazel build --nobuild <target>`, so the measured builds do not pay the fetch cost. All runs then share that
output base, but the server is shut down before every build, so each repeat measures a cold
analysis instead of an incremental re-analysis of the repeat before it - which is what makes the
repeats comparable and their average meaningful. The output base is deleted when the tool finishes.

For every repeat the tool:

1. runs `bazel build <target> --nobuild --memory_profile=<tmp> --memory_profile_stable_heap_parameters=4,4`,
2. parses the retained heap of the *Load and analyze dependencies* phase from the memory profile
   (`analysis_heap_used`, `analysis_heap_committed`, in MB), and
3. reads `bazel info gc-count gc-time max-heap-size peak-heap-size used-heap-size used-heap-size-after-gc`.

`--memory_profile_stable_heap_parameters=4,4` forces 4 full GCs (4s apart) before each phase's
heap is recorded, so `analysis_heap_used` reflects the retained live set after analysis - a
stable, comparable number rather than a noisy point-in-time snapshot. This is the headline metric
for the aspect's memory overhead.

Because the server is restarted for every repeat, `peak-heap-size` and the GC counters cover that
one build rather than a whole server lifetime. `max-heap-size` is the JVM's own `-Xmx`, so it is a
constant and its comparison is always zero.

The default `--nobuild` stops after analysis, so the numbers reflect only the phase where the aspect
does its work. Pass `--build` to execute the actions too, which is recorded in the report's `build`
field. Note that bazel then fuses analysis and execution into a single
*Load, analyze dependencies and build artifacts* phase, so the heap numbers of a `--build` run cover
execution as well and are not comparable with the analysis-only ones.

## The report

The report is a textproto with one `metrics` entry per metric, holding the baseline and the
aspect-enabled run side by side:

```textproto
metrics {
  name: "analysis_heap_used"
  baseline {
    values: 2081.2
    values: 2094.7
    values: 2094.4
    avg: 2090.1
    std: 7.71
  }
  aspect {
    values: 2470.1
    values: 2488.3
    values: 2485.5
    avg: 2481.3
    std: 9.80
  }
  cmp: 18.72
}
```

The numbers above are shortened for readability, the report writes full precision.

`values` are the raw per-repeat readings in run order, `avg` is their arithmetic mean and `std`
their sample standard deviation (n-1). `cmp` is the percentage the aspect average adds over the
baseline average.

Read `cmp` together with both deviations: a comparison smaller than the runs' own spread is noise,
not overhead. A statistic that is undefined - `std` of a single repeat, `cmp` against a zero
baseline - is zero, and therefore omitted from the textproto. The baseline run is always measured.

## Manual use

```
bazel run //tools/measure -- <project> -l java,kotlin -r 3
```

`<project>` is a Bazel workspace directory. Its contents are linked into a temporary sandbox and
measured with a fully isolated server (own output roots, no rc files, minimal environment), so
the project's own server and caches are never touched.

## Output

Stdout carries the report and nothing else, so it can be piped into another tool. Everything else -
the phase and every command the tool runs - goes to stderr:

```
warmup
$ .../bazelisk --nosystem_rc --nohome_rc --output_base=... build --lockfile_mode=update --nobuild //...
baseline 1/3
$ .../bazelisk ... shutdown
$ .../bazelisk ... build --lockfile_mode=update --nobuild --memory_profile=... //...
$ .../bazelisk ... info gc-count gc-time max-heap-size ...
baseline 2/3
...
aspect 1/3
...
```

The report goes to a file instead with `--report <file>`.

The output of the nested bazel is always recorded, but only shown when a measurement fails, so a
failure can be diagnosed without the log of a successful benchmark drowning everything else. Pass
`-v/--verbose` to stream it while it runs, together with a stack trace on failure, or `-q/--quiet`
to suppress the diagnostics entirely and print the recorded log only if the measurement fails.

## Bazel rule

`measure_report` runs the tool as a build action against a project downloaded by the
`bazel_registry.project` module extension tag and produces the report as a textproto file. The
project archive is extracted once by a separate, cacheable action; only the measurement itself
re-runs every time:

```python
bazel_registry.project(
    name = "abseil_cpp",
    commit = "20260817.0",
    sha256 = "...",
    url = "https://github.com/abseil/abseil-cpp",
)
```

```python
measure_report(
    name = "report",
    languages = ["cc"],
    project = "@abseil_cpp//:project.zip",
    tags = ["manual"],
)
```

```
bazel build //tools/measure:report
cat bazel-bin/tools/measure/report.textproto
```

The action runs the tool with `--quiet`, so a successful measurement prints nothing - bazel replays
an action's output even on success, and a benchmark's log is long. A failed measurement prints the
tail of the nested bazel log, which bazel then surfaces with the action failure.

Some projects are not a single checkout. `overlays` extracts further archives into a directory of
the project, so a layout that upstream assembles with extra clones - such as intellij-community,
whose `getPlugins.sh` clones the android plugin into `android` - can be pinned as a set of
archives instead:

```python
bazel_registry.project(
    name = "intellij_community",
    commit = "idea/2026.2.2",
    sha256 = "...",
    url = "https://github.com/JetBrains/intellij-community",
)
```

```python
measure_report(
    name = "intellij_report_nobuild",
    languages = ["java", "kotlin"],
    overlays = {"android": "@intellij_android//:project.zip"},
    project = "@intellij_community//:project.zip",
    tags = ["manual"],
)

measure_report(
    name = "intellij_report_build",
    build = True,
    languages = ["java", "kotlin"],
    overlays = {"android": "@intellij_android//:project.zip"},
    project = "@intellij_community//:project.zip",
    tags = ["manual"],
)
```

Overlays are extracted by the same cacheable action as the project, with the same `strip_prefix`,
so the result is one project tree laid out the way a checkout would be.

The action is never cached and always re-measures. Benchmark targets should be tagged `manual`
and built alone for stable numbers.
