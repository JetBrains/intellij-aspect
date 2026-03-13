/*
 * Copyright 2026 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.aspect.tools;

import com.google.devtools.build.runfiles.AutoBazelRepository;
import com.google.devtools.build.runfiles.Runfiles;
import java.io.IOException;
import java.nio.file.Path;

@AutoBazelRepository
public class RunfilesRepo {

  private static Runfiles runfiles;

  public static synchronized Path rlocation(String path) throws IOException {
    if (runfiles == null) {
      runfiles = Runfiles.preload().withSourceRepository(AutoBazelRepository_RunfilesRepo.NAME);
    }

    final var location = runfiles.rlocation("_main/" + path);
    if (location == null) {
      throw new IOException("Cannot find runfile: " + path);
    }

    return Path.of(location);
  }
}
