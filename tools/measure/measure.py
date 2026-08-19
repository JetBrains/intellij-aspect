#!/usr/bin/env python3
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

import argparse
import csv
import os
import re
import shlex
import statistics
import subprocess
import sys
import tempfile

try:
    import readline  # enables prefilling the interactive prompts
except ImportError:  # not available on all platforms; prefill degrades to a no-op
    readline = None

# The `bazel info` keys we collect, in CSV column order. Each maps to a short CSV name.
INFO_KEYS = [
    ("gc-count", "gc_count"),
    ("gc-time", "gc_time_ms"),
    ("max-heap-size", "max_heap_mb"),
    ("peak-heap-size", "peak_heap_mb"),
    ("used-heap-size", "used_heap_mb"),
    ("used-heap-size-after-gc", "used_heap_after_gc_mb"),
]

# Metrics pulled from Bazel's `--memory_profile` output (converted to MB). We only keep the
# "Load and analyze dependencies" phase heap values -- the retained live set after analysis,
# which is where an aspect's overhead shows. Forced GCs (see stable-heap params) make this a
# stable, comparable number rather than a noisy point-in-time snapshot.
PROFILE_METRICS = [
    ("analysis_heap_used_mb", "analysis_heap_used_mb"),
    ("analysis_heap_committed_mb", "analysis_heap_committed_mb"),
]

# The memory-profile phase/region we read the headline numbers from.
PROFILE_PHASE = "Load and analyze dependencies"
PROFILE_REGION = "heap"

# All numeric metrics in CSV order: profile heap values, then Bazel's reported elapsed time,
# then the info keys. Wall time is no longer measured in Python -- the stable-heap GCs make
# total build time meaningless, so `bazel_elapsed_s` (parsed from Bazel's output) is kept
# only as a comparative figure.
METRICS = PROFILE_METRICS + [("bazel_elapsed_s", "bazel_elapsed_s")] + INFO_KEYS

_NUMBER_RE = re.compile(r"-?\d+(?:\.\d+)?")

# Matches Bazel's final "INFO: Elapsed time: 97.175s, Critical Path: 0.00s" line.
_ELAPSED_RE = re.compile(r"Elapsed time:\s*([\d.]+)s")

# The flags we always inject into the build so each run emits a comparable memory profile.
# 4,4 = do 4 full GCs, waiting 4s between them, before recording each phase's heap.
STABLE_HEAP_PARAMETERS = "4,4"


def build_header():
    """Fixed CSV header: label, repeats, then mean+std pairs for each metric."""
    header = ["label", "repeats"]
    for _, name in METRICS:
        header.append(name)
        header.append(name + "_std")
    return header


def parse_info(output):
    """Parse ``bazel info`` output into {info-key: float | None}, stripping units (ms, MB).

    A value of None means the key was present but non-numeric (e.g. ``unknown``).
    """
    values = {}
    for line in output.splitlines():
        if ":" not in line:
            continue
        key, _, rest = line.partition(":")
        key = key.strip()
        match = _NUMBER_RE.search(rest)
        values[key] = float(match.group()) if match else None
    return values


def parse_memory_profile(path):
    """Parse a ``--memory_profile`` file into the analysis-phase heap metrics we record.

    Each line is ``<phase>:<region>:<metric>:<bytes>`` (phase names contain spaces but no
    colons, e.g. ``Load and analyze dependencies:heap:used:2075874840``). We split from the
    right so the phase name survives, then pull the ``used`` and ``commited`` (note Bazel's
    single-m spelling) values for the analysis phase's heap, converting bytes to MB.

    Returns {metric_name: float | None}; a missing value is None (rendered ``n/a`` later).
    """
    values = {name: None for _, name in PROFILE_METRICS}
    try:
        with open(path) as fh:
            content = fh.read()
    except OSError:
        return values

    wanted = {
        "used": "analysis_heap_used_mb",
        "commited": "analysis_heap_committed_mb",
    }
    for line in content.splitlines():
        parts = line.strip().split(":")
        if len(parts) < 4:
            continue
        phase = ":".join(parts[:-3])
        region, metric, raw = parts[-3], parts[-2], parts[-1]
        if phase != PROFILE_PHASE or region != PROFILE_REGION or metric not in wanted:
            continue
        try:
            values[wanted[metric]] = int(raw) / 1024 / 1024
        except ValueError:
            values[wanted[metric]] = None
    return values


def bazel_env():
    env = os.environ.copy()

    # drop bazel/bazelisk injected variables
    env.pop("BAZELISK_SKIP_WRAPPER", None)
    env.pop("BUILD_WORKING_DIRECTORY", None)

    # remove all modifications to PATH made by bazelisk
    parts = env.get("PATH", "").split(":")
    parts = [p for p in parts if "bazelisk/downloads" not in p]
    env["PATH"] = ":".join(parts)

    return env


def run_command(argv, cwd, env=None):
    """Run ``argv``, teeing its combined output to the terminal while capturing it.

    Returns (exit_code, output_text). We capture so the caller can parse Bazel's reported
    elapsed time, but still stream lines live so a long build shows progress.
    """
    proc = subprocess.Popen(
        argv,
        cwd=cwd,
        env=env,
        stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT,
        text=True,
        bufsize=1,
    )
    lines = []
    for line in proc.stdout:
        sys.stdout.write(line)
        lines.append(line)
    proc.wait()
    return proc.returncode, "".join(lines)


def measure_once(build_args, build_dir):
    """One repeat: shutdown, build with an injected memory profile, then read metrics.

    Returns a dict {metric_name: value} on success, or None if the build failed.
    """
    env = bazel_env()

    print("  $ bazel shutdown")
    subprocess.run(
        ["bazel", "shutdown"],
        cwd=build_dir,
        env=env,
        stdout=subprocess.DEVNULL,
        stderr=subprocess.DEVNULL,
    )

    profile_fd, profile_path = tempfile.mkstemp(prefix="mem_profile_", suffix=".txt")
    os.close(profile_fd)
    try:
        build_argv = (
            ["bazel", "build"]
            + shlex.split(build_args)
            + [
                f"--memory_profile={profile_path}",
                f"--memory_profile_stable_heap_parameters={STABLE_HEAP_PARAMETERS}",
            ]
        )
        print("  $ " + " ".join(build_argv))
        code, output = run_command(build_argv, cwd=build_dir, env=env)
        if code != 0:
            print(f"  ! build exited with code {code}")
            return None

        match = _ELAPSED_RE.search(output)
        elapsed = float(match.group(1)) if match else None

        info_args = ["bazel", "info"] + [key for key, _ in INFO_KEYS]
        print("  $ " + " ".join(info_args))
        result = subprocess.run(
            info_args, cwd=build_dir, env=env, capture_output=True, text=True
        )
        if result.returncode != 0:
            print(f"  ! bazel info failed with code {result.returncode}")
            sys.stderr.write(result.stderr)
            return None
        print(result.stdout.rstrip())

        profile = parse_memory_profile(profile_path)
    finally:
        try:
            os.remove(profile_path)
        except OSError:
            pass

    # A key that is absent or reported as `unknown` is stored as None (not a failure);
    # it is skipped when averaging and rendered as n/a only if unknown across all repeats.
    parsed = parse_info(result.stdout)
    sample = {"bazel_elapsed_s": elapsed}
    sample.update(profile)
    for key, name in INFO_KEYS:
        sample[name] = parsed.get(key)

    if elapsed is not None:
        print(f"  bazel elapsed: {elapsed:.3f}s")
    used = sample.get("analysis_heap_used_mb")
    if used is not None:
        print(f"  analysis heap used: {used:.1f} MB")
    return sample


def aggregate(samples):
    """Turn a list of per-repeat metric dicts into a CSV row (label added by caller)."""
    row = {"repeats": len(samples)}
    for _, name in METRICS:
        # Skip unknowns (None); average only the repeats that reported a number.
        values = [s[name] for s in samples if s[name] is not None]
        # Elapsed time keeps 3 decimals; heap/GC counts round to 1 decimal.
        digits = 3 if name == "bazel_elapsed_s" else 1
        if not values:
            row[name] = "n/a"
            row[name + "_std"] = ""
            continue
        row[name] = _fmt(statistics.fmean(values), digits)
        if len(values) > 1:
            row[name + "_std"] = _fmt(statistics.stdev(values), digits)
        else:
            row[name + "_std"] = ""
    return row


def _fmt(value, digits):
    """Round to `digits`, but drop a trailing .0 so whole numbers stay integers."""
    rounded = round(value, digits)
    return int(rounded) if rounded == int(rounded) else rounded


def write_csv(path, header, rows):
    """Rewrite the whole CSV so it always contains every row collected so far."""
    with open(path, "w", newline="") as fh:
        writer = csv.writer(fh)
        writer.writerow(header)
        for row in rows:
            writer.writerow([row.get(col, "") for col in header])


def prompt(message, prefill=""):
    """Read a line, treating EOF (Ctrl-D) like an exit request.

    If ``prefill`` is given and readline is available, the input line starts
    pre-populated with that text so the user can edit it in place.
    """
    if prefill and readline is not None:
        readline.set_startup_hook(lambda: readline.insert_text(prefill))
    try:
        return input(message)
    except EOFError:
        print()
        return None
    finally:
        if readline is not None:
            readline.set_startup_hook()


def parse_script(path):
    """Read a batch file of ``<label>: <build args>`` lines into (label, build_args) tuples.

    Blank lines and lines starting with ``#`` are ignored. The split is on the *first* colon,
    so build args may themselves contain colons (e.g. ``//config:aspect.bzl%intellij_aspect``).
    Raises ValueError with a ``path:lineno`` prefix on a malformed line.
    """
    configs = []
    with open(path) as fh:
        for lineno, raw in enumerate(fh, 1):
            line = raw.strip()
            if not line or line.startswith("#"):
                continue
            label, sep, build_args = line.partition(":")
            label = label.strip()
            build_args = build_args.strip()
            if not sep or not label or not build_args:
                raise ValueError(
                    f"{path}:{lineno}: expected '<label>: <build args>', got: {line}"
                )
            configs.append((label, build_args))
    return configs


def run_configuration(label, build_args, args, header, rows):
    """Run ``args.repeat`` measurements of one configuration; append+write its CSV row.

    Returns True if a row was recorded, False if a run failed (the config is discarded).
    """
    samples = []
    for i in range(args.repeat):
        print(f"\n[{label}] run {i + 1}/{args.repeat}")
        sample = measure_once(build_args, args.build_dir)
        if sample is None:
            print("  run failed; discarding this configuration\n")
            return False
        samples.append(sample)

    row = aggregate(samples)
    row["label"] = label
    rows.append(row)
    write_csv(args.output, header, rows)

    print(f"\n  wrote row -> {args.output}:")
    print("  " + ",".join(str(row.get(col, "")) for col in header))
    print()
    return True


def run_batch(configs, args, header, rows):
    """Run each (label, build_args) from a script file non-interactively."""
    print(f"Running {len(configs)} configuration(s) from {args.script}\n")
    for label, build_args in configs:
        run_configuration(label, build_args, args, header, rows)


def run_interactive(args, header, rows):
    """Prompt for label + build parameters repeatedly until the user quits."""
    last_label = ""
    last_build_args = ""

    print("For each iteration: enter a label and the bazel build parameters")
    print("(targets and extra flags, e.g. '//... --nobuild --aspects=//config:aspect.bzl%intellij_aspect').")
    print(
        "The harness runs `bazel build <params> "
        f"--memory_profile=<tmp> --memory_profile_stable_heap_parameters={STABLE_HEAP_PARAMETERS}`."
    )
    print("Leave the label empty (or type 'q' / 'quit') to finish.\n")

    while True:
        label = prompt("label> ", prefill=last_label)
        if label is None:
            break
        label = label.strip()
        if label == "" or label.lower() in ("q", "quit"):
            break

        build_args = prompt("build args> ", prefill=last_build_args)
        if build_args is None:
            break
        build_args = build_args.strip()
        if not build_args:
            print("  no build parameters entered, skipping\n")
            continue

        last_label = label
        last_build_args = build_args
        run_configuration(label, build_args, args, header, rows)


def main():
    parser = argparse.ArgumentParser(
        description="Interactive harness for repeated Bazel aspect measurements."
    )
    parser.add_argument(
        "--build-dir",
        required=True,
        help="Directory of the Bazel project to measure (bazel commands run here)",
    )
    parser.add_argument(
        "--output",
        default="aspect_measurements.csv",
        help="CSV output path (default: aspect_measurements.csv)",
    )
    parser.add_argument(
        "--repeat",
        type=int,
        default=1,
        help="How many times to run each configuration, averaged into one row (default: 1)",
    )
    parser.add_argument(
        "--script",
        help="Batch file of '<label>: <build args>' lines to run non-interactively "
        "(blank lines and lines starting with '#' are ignored). Without it, the harness "
        "prompts interactively.",
    )
    args = parser.parse_args()

    if args.repeat < 1:
        parser.error("--repeat must be >= 1")
    if not os.path.isdir(args.build_dir):
        parser.error(f"--build-dir is not a directory: {args.build_dir}")

    configs = None
    if args.script:
        if not os.path.isfile(args.script):
            parser.error(f"--script is not a file: {args.script}")
        try:
            configs = parse_script(args.script)
        except ValueError as e:
            parser.error(str(e))
        if not configs:
            parser.error(f"--script contains no configurations: {args.script}")

    header = build_header()
    rows = []

    print("Bazel aspect benchmark harness")
    print(f"Project under test: {args.build_dir}")
    print(f"Writing results to: {args.output}")
    print(f"Repeats per iteration: {args.repeat}")

    try:
        if configs is not None:
            run_batch(configs, args, header, rows)
        else:
            run_interactive(args, header, rows)
    except KeyboardInterrupt:
        print("\nInterrupted.")

    if rows:
        write_csv(args.output, header, rows)
        print(f"\nDone. {len(rows)} row(s) written to {args.output}")
    else:
        print("\nNo measurements recorded.")


if __name__ == "__main__":
    main()
