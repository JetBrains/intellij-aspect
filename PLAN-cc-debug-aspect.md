# Plan: a debug aspect for C and C++ targets

## Goal

Add a second aspect entry point, `intellij_debug_aspect`, next to `intellij_aspect`. It uses the same
module infrastructure and runs one new module, `cc_debug_info`. The debug aspect gives the IDE two things:

1. The **compile and link command lines** of each C or C++ target. CLwB uses them to check for debug
   flags. They replace the `aquery` in `com.google.idea.blaze.clwb.run.DiscoverTargetConfigurations`.
2. A new output group, **`intellij-debug`**, with the files that the debugger reads. These are the
   object files and archives on macOS (OSO), the `.dwp` file with fission, the `.pdb` file with MSVC,
   and the `.dSYM` bundle. Under `--remote_download_toplevel`, Bazel downloads the files of a
   requested output group. So the debugger finds them on disk.

The sync aspect (`intellij_aspect`) must not change. Its modules, its output, and its
`intellij-info.txt` file names stay the same.

## Facts already verified

All checks used Bazel 9.3.1, rules_cc 0.2.25, the apple_support toolchain on macOS, and `-c dbg`.

- `DebugPackageInfo.dwo_files`, `DebugPackageInfo.dwp_file`, and `CcInfo._debug_context` are empty
  on macOS. They hold only fission outputs, and apple_support has no `per_object_debug_info` feature.
- On macOS the binary holds no DWARF. Its N_OSO entries point to the object files:

  ```
  OSO bazel-out/darwin_arm64-dbg/bin/main/_objs/main/main.o
  OSO bazel-out/darwin_arm64-dbg/bin/lib/default/liblib.a(impl.o)
  ```

  For static deps the entry names the **archive member**, not the loose `.o` file.
- Each output group of the cc rules holds only the target's own files. No group is transitive.
  - `cc_library`: `archive` (the `.a`) and `compilation_outputs` (the `.o`).
  - `cc_binary`: `compilation_outputs` (its own `.o`). The default output is only the binary.
  - With `--apple_generate_dsym`, `cc_binary` also gets a `dsyms` group.
- `--remote_download_toplevel` with `--output_groups=+compilation_outputs` downloads `main.o` of the
  top-level binary, but not the `.a` or `.o` files of the deps.
- An aspect that puts the `.a` and `.o` files of all deps into its own output group fixes this.
  After a `--remote_download_toplevel` build, all OSO targets were on disk.
- An aspect can read the command lines through `target.actions`:

  ```
  //lib/default:lib  CppCompile argv has "-g" (36 args)  outputs impl.o, impl.d
  //main:main        CppCompile argv has "-g" (49 args)  outputs main.o, main.d
  //main:main        CppLink    argv has "-Wl,-oso_prefix,__BAZEL_EXECUTION_ROOT__/"
  ```

These were **not** verified, and the tests must cover them:

- `Action.argv` on `CppCompile` and `CppLink` with Bazel 7.7.1 and 8.8.0, and with rules_cc 0.1.1
  and 0.2.9.
- The `DebugPackageInfo` symbol in rules_cc 0.1.1 and in the Bazel 7 builtin rules.
- The OSO entries of a `.dylib` (dynamic linking, the default for `cc_test` on macOS). They probably
  point to the loose `.o` files.

## Design

### The aspect variant

Add the concept of an **aspect variant** with two values: `info` (the sync aspect) and `debug`. Each
module belongs to one variant. `intellij_configure_aspect` takes the variant as a parameter.

The variant controls three things:

1. Which modules the SDK puts into the generated `config/aspect.bzl` for each entry point.
2. The file name suffix of the per-target text proto. The two aspects must not write the same path
   (see "File name collision" below).
3. `EXTRA_RULES`. Only the `info` variant writes a file for rules without module data.

### File name collision

`ide_info.write` names the file `<hash(key)>.<name>.intellij-info.txt`. The key does not depend on
the aspect variant. If both aspects use this name:

- In one build, Bazel reports conflicting actions for the same output path.
- In separate builds, each build overwrites the other's file, and the write actions run again.

So the debug variant uses the suffix `.intellij-debug.txt`. The `info` variant keeps
`.intellij-info.txt`.

### One build runs one variant

Both aspects declare `provides = [IntelliJInfo]`, so one build must not apply both to the same target.
Document this in the KDoc-style comment of `intellij_configure_aspect` and in the README. Do not try to
support it.

### The new output group

Add `intellij-debug` to `intellij_output_groups` and to the `OutputGroups` enum in the SDK. Only the
`cc_debug_info` module adds files to it. The debug aspect also emits `intellij-info` with the text
proto files, as the sync aspect does.

## The proto message

Add to `sdk/proto/ide_info.proto`. Field number 124 is the next free one in the C range (120 to 123
are in use).

```proto
// Debug information for a C or C++ target, reported by the debug aspect.
message CcDebugInfo {
  message Action {
    // The action mnemonic, e.g. CppCompile or CppLink.
    string mnemonic = 1;

    // The full command line. A params file shows as one `@<path>` argument, and its content is not
    // expanded.
    repeated string arguments = 2;

    // All outputs of the action, e.g. the object file, the .d file, the binary or the .pdb file.
    repeated ArtifactLocation outputs = 3;
  }

  // One entry for each compile action of the target, in the order of target.actions.
  repeated Action compile_actions = 1;

  // The link actions of the target. Empty for a cc_library that only creates an archive.
  repeated Action link_actions = 2;
}
```

And in `TargetIdeInfo`:

```proto
  CcDebugInfo cc_debug_info = 124;
```

The files in the `intellij-debug` group are **not** in the message. The IDE gets them from the BEP
named sets of the output group, as for `intellij-build`.

## The files for the `intellij-debug` group

Collect them for each target that passes the guard (see the module code). The module adds only the
target's own files. The builder merges the groups of the deps (`_merge_dependencies`), so the module
must not flatten a transitive set.

| Source | Files | Why |
|---|---|---|
| `OutputGroupInfo.compilation_outputs` | `.o`, `.pic.o`, `.obj` | OSO of the binary's own sources, and OSO of each `.dylib`. |
| `OutputGroupInfo.archive` | `.a`, `.pic.a`, `.lib` | OSO of static deps names `lib.a(member.o)`. |
| `OutputGroupInfo.dsyms` | the `.dSYM` directory | With `--apple_generate_dsym`, lldb reads this. |
| `OutputGroupInfo.pdb_file` | `.pdb` | MSVC debug info of a binary. |
| `DebugPackageInfo.dwp_file` | `.dwp` | Fission debug info for gdb. CLwB adds `--fission=yes` for gdb. |

Rules:

- Drop the header tokens. With `process_headers_in_dependencies`, `compilation_outputs` also holds
  `.processed` files.
- Do not add `dynamic_library`. The `.dylib` and `.so` files are runtime files. Bazel downloads them
  with the runfiles of the top-level target.
- Do not read `CcInfo.linking_context.linker_inputs`. It is transitive, so a `to_list()` on each
  target is quadratic over the graph.
- Use `intellij_common.depset` so an empty set becomes `None`.

## The module: `modules/cc_debug_info.bzl`

Draft. Follow the style of `modules/cc_info.bzl`.

```starlark
load("@rules_cc//cc:defs.bzl", "CcInfo", "DebugPackageInfo")
load("//common:artifact_location.bzl", "artifact_location")
load("//common:common.bzl", "intellij_common")
load("//common:output_groups.bzl", "intellij_output_groups")
load("//common:provider.bzl", "intellij_provider")
load(":module.bzl", "intellij_module")

COMPILE_MNEMONICS = ["CppCompile"]
LINK_MNEMONICS = ["CppLink"]

# output groups of the cc rules that hold files the debugger reads
DEBUG_OUTPUT_GROUPS = ["compilation_outputs", "archive", "dsyms", "pdb_file"]

def _aspect_guard(target, ctx):
    """Returns true if the aspect should be applied to the current target."""
    if CcInfo not in target:
        return False

    # go targets always provide CcInfo, usually it's empty and even if it isn't we don't handle it
    if ctx.rule.kind.startswith("go_"):
        return False

    # targets built under exec configuration are never linked into the debuggee
    if intellij_common.is_exec_configuration(ctx):
        return False

    return True

def _action_info(action):
    """Converts an action to a CcDebugInfo.Action. Returns None if the command line is not available."""
    if action.argv == None:
        return None

    return intellij_common.struct(
        mnemonic = action.mnemonic,
        arguments = action.argv,
        outputs = artifact_location.from_files(action.outputs.to_list()),
    )

def _collect_actions(target, mnemonics):
    actions = [_action_info(it) for it in target.actions if it.mnemonic in mnemonics]
    return [it for it in actions if it]

def _collect_debug_files(target):
    """Collects the target's own files that the debugger reads."""
    files = []

    if OutputGroupInfo in target:
        groups = target[OutputGroupInfo]
        for name in DEBUG_OUTPUT_GROUPS:
            if hasattr(groups, name):
                files.extend([f for f in getattr(groups, name).to_list() if not f.basename.endswith(".processed")])

    if DebugPackageInfo in target and target[DebugPackageInfo].dwp_file:
        files.append(target[DebugPackageInfo].dwp_file)

    return files

def _implementation(target, ctx, attr):
    if not _aspect_guard(target, ctx):
        return None

    return intellij_module.result(
        outputs = {intellij_output_groups.DEBUG: intellij_common.depset(_collect_debug_files(target))},
        value = intellij_common.struct(
            compile_actions = _collect_actions(target, COMPILE_MNEMONICS),
            link_actions = _collect_actions(target, LINK_MNEMONICS),
        ),
    )

_aspect = intellij_module.aspect(
    provider = intellij_provider.CcDebugInfo,
    implementation = _implementation,
    field = "cc_debug_info",
)

module = intellij_module.define(
    file = "cc_debug_info",
    aspect = _aspect,
    aspect_providers = [CcInfo],
    rulesets = ["CC"],
    variant = "debug",
)
```

Notes on the draft:

- **cc_proto_library.** `cc_info.bzl` skips `cc_proto_library`, because the sync attaches to
  `proto_library`. The debug module must **not** skip it, because its code is linked into the
  binary. Check which target holds the compile actions and the `archive` group. That is either
  `cc_proto_library`, or `proto_library` under `cc_proto_aspect`. `aspect_providers = [CcInfo]` makes
  the aspect see the `CcInfo` of `cc_proto_aspect`. But `target.actions` probably holds only the
  actions of the rule, not those of other aspects. If the module misses these files, record that as a
  known gap. Do not block the change on it.
- **`DebugPackageInfo` load.** If rules_cc 0.1.1 or the Bazel 7 builtin mode does not export the
  symbol from `@rules_cc//cc:defs.bzl`, use the same approach that `cc_info.bzl` and the `builtin`
  fixture use for `CcInfo`.
- **Objective-C.** `ObjcCompile` and `objc_library` are out of scope. Add the mnemonics later if
  needed.
- **Memory.** `action.argv` expands the command line in the analysis phase. The debug aspect runs only
  for debug builds, so the sync benchmark does not change. Still, measure one large C++ project with
  `tools/` (see README "Performance tests") and put the number in the commit message.

## File-by-file changes

### Starlark

1. **`common/provider.bzl`**
   - Add `_IntelliJCcDebugInfo = _create_module_provider()`.
   - Put it into `_ORDERED` after `_IntelliJCcInfo`.
   - Export it as `CcDebugInfo` in `intellij_provider`.
2. **`common/output_groups.bzl`**
   - Add `_DEBUG = "intellij-debug"`.
   - Export it as `DEBUG` in `intellij_output_groups`.
3. **`modules/module.bzl`**
   - Add `variant = "info"` to `_define`, and put it into the returned struct.
   - Allowed values are `"info"` and `"debug"`. Fail on other values.
4. **`modules/cc_debug_info.bzl`**: new, see above.
5. **`common/ide_info.bzl`**
   - Give `_write_info` a `variant` parameter.
   - The file name suffix is `intellij-info.txt` for `info` and `intellij-debug.txt` for `debug`.
   - Keep the hash and the short name part as they are.
   - `_write_toolchain_info` keeps the `info` variant.
6. **`intellij/aspect.bzl`**
   - Give `intellij_configure_aspect(modules, container)` a `variant = "info"` parameter.
   - Fail if a module's `variant` is not the aspect's variant.
   - Create the implementation as a closure over the variant. `modules/module.bzl` already uses a
     nested function as an aspect implementation, so this pattern works.
   - Pass the variant to `_merge_target_info` and from there to `ide_info.write`.
   - Use `EXTRA_RULES` only for the `info` variant.
7. **`config/aspect.bzl`** (the BCR entry point)
   - Add `DEBUG_MODULES = [cc_debug_info]`.
   - Add `intellij_debug_aspect = intellij_configure_aspect(modules = DEBUG_MODULES, container = "//:module_container", variant = "debug")`.
   - Do not add the module to `MODULES`.
8. **`private/rules/kt_aspect_enum.bzl`**
   - Add a `variant` attribute to `_aspect_module`, and pass `module.variant` from the macro.
   - Add `val variant: String` (or an `AspectVariant` enum) to the generated `Modules` enum.
9. **`sdk/lib/BUILD`**
   - Generate the enum from `MODULES + DEBUG_MODULES + [rules_proto_info]`.

### SDK (Kotlin)

1. **`sdk/lib/aspectchoice.kt`**
   - Add `DEBUG("intellij-debug")` to `OutputGroups`.
   - Add `val DEBUG_ASPECT_NAME: String = "config:aspect.bzl%intellij_debug_aspect"`.
   - Give `modulesForRules` a variant parameter. Without it, the sync config would load `cc_debug_info`.
2. **`sdk/lib/config.kt`**
   - In `generateAspectConfig`, write both entry points into the generated `aspect.bzl`.
   - Each entry point gets the modules of its variant, filtered by the rulesets.
   - With no C ruleset, `DEBUG_MODULES` is empty. Still write `intellij_debug_aspect`, so the IDE
     can always reference it.
3. **`sdk/lib/readtarget.kt`**
   - No change needed. `readTargetFromFile` parses a `TargetIdeInfo`, and `cc_debug_info` is one more
     field.

### Proto

1. **`sdk/proto/ide_info.proto`**: add `CcDebugInfo` and the field `cc_debug_info = 124`.

### Tests

1. **`testing/rules/worker/worker.proto`**
   - Add `DEBUG = 3` to `WorkArguments.Outputs`.
   - With `DEBUG`, the worker applies `DEBUG_ASPECT_NAME` and requests `intellij-info` and
     `intellij-debug`.
2. **`testing/rules/fixture.bzl`**: add `"debug"` to the `outputs` attribute mapping.
3. **`testing/rules/worker/builder.kt`**: choose the aspect name and the output groups from `Outputs`.
4. **`testing/rules/utils/fixture.kt`**: add `findCcDebugInfo(label)`, like `findCIdeInfo`.
5. **`testing/fixtures/cpp/BUILD`**: add two fixtures.
   - `debug`: reuse `simple/**` with `strip_prefix = "simple"`, `extra_flags = ["-c", "dbg"]`,
     `outputs = "debug"`, `modules = _MODULES`, and `targets = ["//:main", "//:test"]`.
   - `debug_apple_support`: the same with apple_support, `bazel = ">=9"`, macOS only, like the
     `apple_support` fixture.
6. **`testing/tests/cpp/DebugInfoTest.kt`** (new) and a `test_runner` in `testing/tests/cpp/BUILD`.
   Test these cases:
   - `//lib:lib` has one compile action. Its arguments contain `-g`, and its outputs contain
     `lib/_objs/lib/source.o` (or `.pic.o`, or `.obj` on Windows).
   - `//lib:lib` has no link action.
   - `//:main` has one `CppLink` action, and its outputs contain the binary.
   - The `intellij-debug` group contains the `.a` file of `//lib:lib` and the `.o` file of `//:main`.
   - The `intellij-debug` group contains no `.processed` file and no file under an `-exec` directory.
     Reuse the `tool` fixture for the exec case.
   - The text proto files end in `.intellij-debug.txt`.
   - `debug_apple_support`: the link arguments of `//:main` contain `-oso_prefix`.
7. **Existing tests** must pass without changes. In particular, check these:
   - `ModuleEnumTest`: the new module file is in `archive_ide`.
   - `DeployTest` and `DeployFileTest`: the sync config does not load `cc_debug_info`.
   - The sync info file names do not change.
8. Run the full matrix: Bazel 7.7.1, 8.8.0, 9.2.0, and the rules_cc versions in `_MODULES`. This
   verifies `Action.argv` on the native actions of older versions.

### Documentation

1. **`README.md`**
   - Add `intellij-debug` to "Output groups".
   - Add a short section on the two entry points, and say that one build runs only one of them.

## Out of scope

These are for the CLwB and IDE side, after a release of the aspect:

- Run the debug aspect in the debug build. First build only `--output_groups=intellij-info`, which
  runs only write actions. Then run the full build with `+intellij-debug`. The second build reuses the
  analysis cache.
- Replace the `aquery` in `DiscoverTargetConfigurations` with `cc_debug_info`. Check that
  `TargetKey.configuration` gives what `findCompilerKind` needs. Today it matches the aquery
  configuration checksum.
- `DebugInfoCheck.checkLinkAction` checks for `-oso_prefix,.`. The apple_support toolchain uses
  `-Wl,-oso_prefix,__BAZEL_EXECUTION_ROOT__/`, so the check probably flags a correct target.
- `BazelDebugFlagsBuilder.kt:111` passes `--remote_download_regex='.*_objs/.*.o$'`.
  `GeneralCommandLine` uses no shell, so the quotes are part of the regex. The regex also misses the
  `.a` files. The `intellij-debug` group replaces this flag.
