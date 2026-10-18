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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Fast scanner which extracts the string values of a specific key without
 * building the JSON model. Operates directly on the UTF-8 bytes, this is safe
 * since all structural characters are ASCII and multibyte UTF-8 sequences never
 * contain ASCII bytes. Only matched values are decoded, keys are compared in
 * place. The structure of the document is not validated.
 */
public final class JsonScanner {

  // bounds of the last string, the content is [stringStart, stringEnd)
  private int stringStart = 0;
  private int stringEnd = 0;

  private final ArrayList<String> result = new ArrayList<>();

  private final byte[] content;
  private final byte[] needle;

  private JsonScanner(byte[] content, byte[] needle) {
    this.content = content;
    this.needle = needle;
  }

  /**
   * Returns the string values of all object entries with the given key, in
   * document order and independent of their nesting depth.
   */
  public static List<String> scan(Path file, String key) throws IOException {
    final var input = Files.readAllBytes(file);
    return new JsonScanner(input, key.getBytes(StandardCharsets.UTF_8)).scan();
  }

  private List<String> scan() {

    try {
      for (var i = 0; i < content.length; i++) {
        switch (content[i]) {
          case '"' -> {
            stringStart = i + 1;
            stringEnd = findStringEnd(stringStart);
            i = stringEnd;
          }

          case ':' -> {
            // the last string was a key, since colons only occur after keys
            if (!Arrays.equals(content, stringStart, stringEnd, needle, 0, needle.length)) {
              continue;
            }

            i = skipWhitespace(content, i + 1);
            if (i >= content.length || content[i] != '"') {
              throw new IllegalStateException("value of '%s' is not a string at offset %d".formatted(key, i));
            }

            stringStart = i + 1;
            stringEnd = findStringEnd(stringStart);
            i = stringEnd;

            result.add(decodeString(stringStart, stringEnd));
          }
          default -> {
          }
        }
      }
    } catch (IllegalStateException e) {
      throw new IOException("Failed to scan '%s'".formatted(file), e);
    }

    return result;
  }

  /**
   * Returns the index of the closing quote of the string starting at start.
   */
  private int findStringEnd(int start) {
    for (var i = start; i < content.length; i++) {
      switch (content[i]) {
        case '"' -> {
          return i;
        }
        case '\\' -> {
          i++;
        }
        default -> {
          // nothing todo, consume character
        }
      }
    }

    throw new IllegalStateException("unterminated string at offset %d".formatted(start - 1));
  }

  private static int skipWhitespace(byte[] input, int pos) {
    while (pos < input.length && (input[pos] == ' ' || input[pos] == '\t' || input[pos] == '\n' || input[pos] == '\r')) {
      pos++;
    }

    return pos;
  }

  /**
   * Decodes the string content [start, end), the fast path is a single copy.
   */
  private String decodeString(int start, int end) {
    var escape = indexOf(content, start, end, (byte) '\\');
    if (escape < 0) {
      return new String(content, start, end - start, StandardCharsets.UTF_8);
    }

    final var builder = new StringBuilder(end - start);
    var runStart = start;

    while (escape >= 0) {
      builder.append(new String(content, runStart, escape - runStart, StandardCharsets.UTF_8));

      // findStringEnd guarantees that an escape is never the last byte
      runStart = escape + 2;
      switch (content[escape + 1]) {
        case '"' -> builder.append('"');
        case '\\' -> builder.append('\\');
        case '/' -> builder.append('/');
        case 'b' -> builder.append('\b');
        case 'f' -> builder.append('\f');
        case 'n' -> builder.append('\n');
        case 'r' -> builder.append('\r');
        case 't' -> builder.append('\t');
        case 'u' -> {
          // surrogate pairs are encoded as two escapes and end up as a valid pair in the builder
          if (escape + 6 > end) {
            throw new IllegalStateException("incomplete unicode escape at offset %d".formatted(escape));
          }

          final var hex = new String(content, escape + 2, 4, StandardCharsets.US_ASCII);
          try {
            builder.append((char) Integer.parseInt(hex, 16));
          } catch (NumberFormatException e) {
            throw new IllegalStateException("invalid unicode escape at offset %d".formatted(escape));
          }

          runStart = escape + 6;
        }
        default -> throw new IllegalStateException("invalid escape sequence at offset %d".formatted(escape));
      }

      escape = indexOf(content, runStart, end, (byte) '\\');
    }

    builder.append(new String(content, runStart, end - runStart, StandardCharsets.UTF_8));
    return builder.toString();
  }

  private static int indexOf(byte[] input, int start, int end, byte value) {
    for (var i = start; i < end; i++) {
      if (input[i] == value) {
        return i;
      }
    }

    return -1;
  }
}
