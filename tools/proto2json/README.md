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

## The JSON

The JSON follows the standard proto3 mapping, with the field names of the `.proto` files (e.g.
`dependency_type`), so `jq` paths read like the textproto. Note that:

- fields with their default value are omitted, e.g. an `aspect_deployment` of `BCR` or an empty
  list, so `jq` sees them as `null`. Use `//` for a fallback, e.g. `.config.aspect_deployment // "BCR"`,
  and `[]?` to iterate over a list that might be absent;
- `bytes` fields are base64 encoded, e.g. the captured files in `generated_files` of a fixture,
  decode them with `@base64d`.

## Converting many files in parallel

Every invocation starts a JVM, so converting many fixtures takes a while. They can be converted in
parallel, but each process has to write its own file, otherwise the outputs interleave:

```sh
out=$(mktemp -d)
ls bazel-bin/testing/fixtures/cpp/*.intellij-aspect-fixture \
  | xargs -P8 -I{} sh -c 'bazel-bin/tools/proto2json/proto2json -i "$1" -o "$2/$(basename "$1").json"' _ {} "$out"

jq '{file: input_filename, bazel: .config.bazel_version}' "$out"/*.json
```
