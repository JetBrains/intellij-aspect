# `measure` - aspect analysis benchmark tool

> **Slop-code warning:** This tool was AI-generated and has only been manually tested.
> It has no automated tests and has not been reviewed for correctness.
> Treat its output as indicative, and verify anything you rely on.

Interactive tool for measuring the cost of running the IntelliJ aspect (or any other
`--aspects=...` configuration) over a Bazel project. For each configuration you give it,
it runs the build repeatedly, times it, and records heap/GC statistics into a CSV so you
can compare a baseline against an aspect-enabled run.

## What it measures

For each iteration you enter a `label` and the **bazel build parameters** (targets plus any
extra flags — *not* a full command). The harness then:

1. runs `bazel shutdown` (so each measurement starts from a cold analysis cache),
2. runs `bazel build <your params> --memory_profile=<tmp> --memory_profile_stable_heap_parameters=4,4`,
3. parses the retained heap of the **"Load and analyze dependencies"** phase from that
   profile (`analysis_heap_used_mb`, `analysis_heap_committed_mb`),
4. records the elapsed time Bazel reports (`bazel_elapsed_s`), and
5. reads `bazel info gc-count gc-time max-heap-size peak-heap-size used-heap-size used-heap-size-after-gc`.

`--memory_profile_stable_heap_parameters=4,4` forces 4 full GCs (4s apart) before each
phase's heap is recorded, so `analysis_heap_used_mb` reflects the **retained live set** after
analysis — a stable, comparable number rather than a noisy point-in-time snapshot. This is
the headline metric for an aspect's memory overhead.

> **Note:** because those forced GCs run inside the build, `bazel_elapsed_s`, `gc-count`, and
> `gc-time` are inflated and only meaningful *comparatively* between runs — not as absolute
> timings.

Results are written to a CSV, one row per configuration, with a `label`, the `repeats`
count, and — for each metric — the mean plus its sample standard deviation (`*_std`).
The CSV is rewritten in full after every iteration, so it is always complete even if you
stop early. Metrics that are absent or reported as `unknown` are skipped when averaging; if a
metric is missing across all repeats it is written as `n/a`.

## Measure the analysis phase only

**The recommended way to benchmark an aspect is to pass `//... --nobuild` as the build
parameters.** The `--nobuild` flag stops after loading and analysis, so the numbers reflect
only the analysis phase — where aspects do their work — and are not dominated by (or noisy
from) actual action execution and caching. Use the same target pattern for both the baseline
and the aspect run so they are comparable. At the prompts:

```
label> baseline
build args> //... --nobuild

label> aspect
build args> //... --nobuild --aspects=//config:aspect.bzl%intellij_aspect
```

## Scripting the measurements

To run a fixed set of configurations non-interactively, pass `--script <file>` where each
line is `<label>: <build args>`. Blank lines and lines starting with `#` are ignored; the
label is split off at the first colon, so build args may contain colons. Each configuration
is run `--repeat` times and written as one CSV row, exactly as in interactive mode.

```
# measurements.txt
baseline: //... --nobuild
aspect:   //... --nobuild --aspects=//config:aspect.bzl%intellij_aspect
```

```
bazel run //tools/measure -- \
  --build-dir /path/to/project \
  --output results.csv \
  --repeat 3 \
  --script measurements.txt
```
