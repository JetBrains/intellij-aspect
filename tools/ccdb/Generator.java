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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

void main(String[] args) throws IOException {
  if (args.length != 2 || !args[1].startsWith("@")) {
    System.err.println("usage: generator <output file> @<params file>");
    System.exit(1);
  }

  final var outputFile = Path.of(args[0]);
  final var compdbFiles = expandParamFile(args[1]);

  final var json = JsonParser.parse(compdbFiles.getFirst());
  System.out.println(json);

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
