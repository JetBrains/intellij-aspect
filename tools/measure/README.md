# `measure` - aspect analysis benchmark tool

> **Slop-code warning:** This tool was AI-generated and has only been manually tested.
> It has no automated tests and has not been reviewed for correctness.
> Treat its output as indicative, and verify anything you rely on.

Interactive tool for measuring the cost of running the IntelliJ aspect (or any other
`--aspects=...` configuration) over a Bazel project. For each configuration you give it,
it runs the build repeatedly, times it, and records heap/GC statistics into a CSV so you
can compare a baseline against an aspect-enabled run.

## What it measures

For every iteration the harness:

1. runs `bazel shutdown` (so each measurement starts from a cold analysis cache),
2. times the build command you provide (wall clock),
3. reads `bazel info gc-count gc-time max-heap-size peak-heap-size used-heap-size used-heap-size-after-gc`.

Results are written to a CSV, one row per configuration, with a `label`, the `repeats`
count, and — for each metric — the mean plus its sample standard deviation (`*_std`).
The CSV is rewritten in full after every iteration, so it is always complete even if you
stop early. Metrics that Bazel reports as `unknown` are skipped when averaging; if a
metric is `unknown` across all repeats it is written as `n/a`.

## Measure the analysis phase only

**The recommended way to benchmark an aspect is `bazel build //... --nobuild`.** The
`--nobuild` flag stops after loading and analysis, so the numbers reflect only the
analysis phase — where aspects do their work — and are not dominated by (or noisy from)
actual action execution and caching. Use the same target pattern for both the baseline
and the aspect run so they are comparable, e.g.:

```
# baseline (no aspect)
bazel build //... --nobuild

# with the aspect
bazel build //... --nobuild --aspects=//config:aspect.bzl%intellij_aspect
```
