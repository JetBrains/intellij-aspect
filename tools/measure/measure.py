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
import statistics
import subprocess
import sys
import time

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

# All numeric metrics in CSV order: wall time first, then the info keys.
METRICS = [("wall_time_s", "wall_time_s")] + INFO_KEYS

_NUMBER_RE = re.compile(r"-?\d+(?:\.\d+)?")


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


def run_command(command, cwd, shell):
    """Run a command, returning (exit_code, elapsed_seconds). Output goes to the terminal."""
    start = time.perf_counter()
    proc = subprocess.run(command, cwd=cwd, shell=shell)
    elapsed = time.perf_counter() - start
    return proc.returncode, elapsed


def measure_once(build_command, build_dir):
    """One repeat: shutdown, timed build, then read info metrics.

    Returns a dict {metric_name: value} on success, or None if the build failed.
    """
    print("  $ bazel shutdown")
    subprocess.run(
        ["bazel", "shutdown"],
        cwd=build_dir,
        stdout=subprocess.DEVNULL,
        stderr=subprocess.DEVNULL,
    )

    print(f"  $ {build_command}")
    code, elapsed = run_command(build_command, cwd=build_dir, shell=True)
    if code != 0:
        print(f"  ! build exited with code {code} (elapsed {elapsed:.3f}s)")
        return None
    print(f"  wall time: {elapsed:.3f}s")

    info_args = ["bazel", "info"] + [key for key, _ in INFO_KEYS]
    print("  $ " + " ".join(info_args))
    result = subprocess.run(
        info_args, cwd=build_dir, capture_output=True, text=True
    )
    if result.returncode != 0:
        print(f"  ! bazel info failed with code {result.returncode}")
        sys.stderr.write(result.stderr)
        return None
    print(result.stdout.rstrip())

    # A key that is absent or reported as `unknown` is stored as None (not a failure);
    # it is skipped when averaging and rendered as n/a only if unknown across all repeats.
    parsed = parse_info(result.stdout)
    sample = {"wall_time_s": elapsed}
    for key, name in INFO_KEYS:
        sample[name] = parsed.get(key)
    return sample


def aggregate(samples):
    """Turn a list of per-repeat metric dicts into a CSV row (label added by caller)."""
    row = {"repeats": len(samples)}
    for _, name in METRICS:
        # Skip unknowns (None); average only the repeats that reported a number.
        values = [s[name] for s in samples if s[name] is not None]
        # Wall time keeps 3 decimals; heap/GC counts round to 1 decimal.
        digits = 3 if name == "wall_time_s" else 1
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
        help="How many times to run each command, averaged into one row (default: 1)",
    )
    args = parser.parse_args()

    if args.repeat < 1:
        parser.error("--repeat must be >= 1")
    if not os.path.isdir(args.build_dir):
        parser.error(f"--build-dir is not a directory: {args.build_dir}")

    header = build_header()
    rows = []
    last_label = ""
    last_command = ""

    print("Bazel aspect benchmark harness")
    print(f"Project under test: {args.build_dir}")
    print(f"Writing results to: {args.output}")
    print(f"Repeats per iteration: {args.repeat}")
    print("For each iteration: enter a label and the full bazel command.")
    print("Leave the label empty (or type 'q' / 'quit') to finish.\n")

    try:
        while True:
            label = prompt("label> ", prefill=last_label)
            if label is None:
                break
            label = label.strip()
            if label == "" or label.lower() in ("q", "quit"):
                break

            command = prompt("command> ", prefill=last_command)
            if command is None:
                break
            command = command.strip()
            if not command:
                print("  no command entered, skipping\n")
                continue

            last_label = label
            last_command = command

            samples = []
            aborted = False
            for i in range(args.repeat):
                print(f"\n[{label}] run {i + 1}/{args.repeat}")
                sample = measure_once(command, args.build_dir)
                if sample is None:
                    print("  run failed; discarding this iteration\n")
                    aborted = True
                    break
                samples.append(sample)

            if aborted or not samples:
                continue

            row = aggregate(samples)
            row["label"] = label
            rows.append(row)
            write_csv(args.output, header, rows)

            print(f"\n  wrote row -> {args.output}:")
            print("  " + ",".join(str(row.get(col, "")) for col in header))
            print()
    except KeyboardInterrupt:
        print("\nInterrupted.")

    if rows:
        write_csv(args.output, header, rows)
        print(f"\nDone. {len(rows)} row(s) written to {args.output}")
    else:
        print("\nNo measurements recorded.")


if __name__ == "__main__":
    main()
