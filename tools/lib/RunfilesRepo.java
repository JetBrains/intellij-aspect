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
