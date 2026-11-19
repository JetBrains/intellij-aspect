# `proto2json` - proto to JSON converter

Converts a textproto or binary proto into JSON, so the output of the aspect and the test fixtures
can be inspected with `jq`. The proto is read from stdin (or `-i <file>`) and the JSON is written
to stdout (or `-o <file>`).

## Usage

The tool converts one proto per invocation. To query many files at once, build it and run the
binary once per file. `jq` reads the concatenated output as a stream of documents and applies the
query to each of them. For example, to print the Bazel version, the modules and the Xcode
toolchains of every `cpp/simple` fixture:

```sh
bazel build //tools/proto2json //testing/fixtures/cpp/...

ls bazel-bin/testing/fixtures/cpp/simple-*.intellij-aspect-fixture \
  | xargs -n1 bazel-bin/tools/proto2json/proto2json -i \
  | jq '{bazel: .config.bazel_version, modules: [.config.modules[] | "\(.name)@\(.version)"], xcode: [.targets[] | select(.c_toolchain_ide_info.xcode_info)]}'
```

Running the binary directly avoids a `bazel run` per file. For a single file `bazel run` works
too, relative paths are resolved against the directory it was invoked from:

```sh
bazel run //tools/proto2json -- -i bazel-bin/pkg/<hash>.lib.intellij-info.txt | jq '.deps[].target.label'
```

## Options

- `-i/--input <file>`: the proto to convert, stdin if absent.
- `-o/--output <file>`: where to write the JSON, stdout if absent.
- `-t/--type <message>`: the message type of the proto, any message of the sdk or fixture protos.
  Defaults to `TestFixture` for `*.intellij-aspect-fixture` files and to `TargetIdeInfo`
  otherwise.
- `-f/--format auto|text|binary`: the encoding of the proto. `auto` tries text first and falls
  back to binary.
- `-v/--verbose`: print stack traces on errors.
