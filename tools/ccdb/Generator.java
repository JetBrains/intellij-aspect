/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.intellij.aspect.tools.ccdb.JsonScanner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

private record CompileCommands(Path file, List<String> sources) {

  public Path getDatabase() {
    // compile commands are always generated as <package>/<target>.compile_commands/compile_commands.json
    return file.getParent();
  }

  public String getPackage() {
    return getDatabase().getParent().toString();
  }

  public Path getBinDirectory() {
    // compile commands are always generated in the bin directory: bazel-out/<config>/bin
    return file.subpath(0, 3);
  }
}

void main(String[] args) throws IOException {
  if (args.length != 2 || !args[1].startsWith("@")) {
    System.err.println("usage: generator <output file> @<params file>");
    System.exit(1);
  }

  final var outputFile = Path.of(args[0]);
  final var compdbFiles = expandParamFile(args[1]);

  for (final var entry : compileCommandsByPackage(compdbFiles).entrySet()) {
    final var file = Path.of(entry.getKey(),".clangd");
    final var content = generateClangdYaml(entry.getValue());

    Files.writeString(file, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
  }

  Files.createFile(outputFile);
}

private List<Path> expandParamFile(String arg) throws IOException {
  assert arg.startsWith("@");

  try (final var lines = Files.lines(Path.of(arg.substring(1)))) {
    return lines.filter(line -> !line.isBlank()).map(Path::of).toList();
  } catch (InvalidPathException e) {
    throw new IOException("Invalid path in params file '%s'".formatted(arg), e);
  }
}

private Map<String, List<CompileCommands>> compileCommandsByPackage(List<Path> files) {
  return files.stream()
    .map(this::parseCompileCommands)
    .filter(Objects::nonNull)
    .collect(Collectors.groupingBy(CompileCommands::getPackage));
}

private CompileCommands parseCompileCommands(Path file) {
  try {
    return new CompileCommands(file, JsonScanner.scan(file, "file"));
  } catch (IOException e) {
    return null;
  }
}

private String generateClangdYaml(List<CompileCommands> commands) {
  return commands.stream()
    .map(this::generateClangdYaml)
    .collect(Collectors.joining("---\n"));
}

private String generateClangdYaml(CompileCommands commands) {
  final var builder = new StringBuilder();
  final var pkg = Path.of(commands.getPackage());

  builder.append("If:\n  PathMatch:\n");
  for (final var source : commands.sources()) {
    var path = Path.of(source);

    if (!path.startsWith(commands.getBinDirectory())) {
      path = commands.getBinDirectory().resolve(path);
    }

    builder.append("    - '");
    builder.append(pkg.relativize(path));
    builder.append("'\n");
  }

  builder.append("CompileFlags:\n  CompilationDatabase: ");
  builder.append(pkg.relativize(commands.getDatabase()));
  builder.append("\n");

  return builder.toString();
}
