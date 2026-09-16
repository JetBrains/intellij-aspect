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

package com.intellij.aspect.testing.rules.worker

import com.intellij.aspect.private.lib.utils.*
import com.intellij.aspect.testing.rules.worker.WorkerProto.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger

fun worker(
  args: Array<String>,
  body: suspend Sandbox.(WorkArguments) -> Unit,
) = runBlocking {
  require(args.contains("--persistent_worker"))

  // the global worker configuration
  val options = parseTextProto<WorkerOptions>(args[0])

  // the global worker logger
  val logger = Logger()

  // persisted working directory outside the sandbox and execroot; reused across
  // worker restarts so caches and output bases stay warm
  val cwd = resolveWorkingDirectory(options.workerDir)

  val shared = createResources(cwd, options)

  require(options.maxServers > 0)

  // keep track of fixed number of bazel servers per version
  val pools = mutableMapOf<String, ServerPool>()

  coroutineScope {
    while (true) {
      val request = WorkRequest.parseDelimitedFrom(System.`in`) ?: break
      val input = parseTextProtoResponseFile<WorkArguments>(request.argumentsList[0])

      val pool = pools.getOrPut(input.config.bazelVersion) { ServerPool(input.config.bazelVersion, options.maxServers) }

      launch(Dispatchers.IO) {
        val server = pool.acquireOrCreate { version, slot -> createServer(logger, cwd, version, slot, shared) }
        val sandboxRoot = Files.createTempDirectory(cwd, "sandbox_").toAbsolutePath()

        server.logger.log("build started: ${input.projectArchive}}")

        val log = ByteArrayOutputStream()

        try {
          sandbox(
            bazelisk = shared.bazeliskBinary,
            version = server.version,
            root = sandboxRoot,
            logger = server.logger.child(out = log),
          ) {
            outputRoot(server.outputRootDirectory)
            outputBase(server.outputBaseDirectory)
            bazeliskHome(shared.bazeliskHomeDirectory)
            diskCache(shared.diskCacheDirectory)
            repoCache(shared.repoCacheDirectory, shared.repoContentsCacheDirectory)
            shared.registryDirectories.forEach(::registry)

            body(input)
          }
          synchronized(System.out) {
            WorkResponse.newBuilder()
              .setExitCode(0)
              .setRequestId(request.requestId)
              .build()
              .writeDelimitedTo(System.out)
          }
        } catch (e: Throwable) {
          val builder = StringBuilder()
          builder.appendLine(log.toString())
          builder.appendLine()
          builder.appendLine(e.stackTraceToString())

          synchronized(System.out) {
            WorkResponse.newBuilder()
              .setExitCode(1)
              .setRequestId(request.requestId)
              .setOutput(builder.toString())
              .build()
              .writeDelimitedTo(System.out)
          }
        } finally {
          pool.release(server)
        }
      }
    }
  }

  for (server in pools.values.flatMap { it.peakAvailable() }) {
    try {
      server.shutdown()
    } catch (_: IOException) {
      // best effort shutdown
    }
  }
}

/**
 * Resolves the worker's persistent working directory. When [configured] is set it is used verbatim
 * (expanding a leading ~). Otherwise, a stable directory is derived under the system temp directory.
 */
@Throws(IOException::class)
private fun resolveWorkingDirectory(configured: String): Path {
  val base = if (configured.isNotBlank()) {
    resolvePath(configured)
  } else {
    val projectName = Path.of("").toAbsolutePath().fileName.toString()
    resolveTempDirectory().resolve("bazel_worker_" + stableHash(projectName))
  }

  return Files.createDirectories(base).toAbsolutePath()
}

/** Short, stable hex digest of [value], independent of JVM version. */
private fun stableHash(value: String): String {
  val digest = MessageDigest.getInstance("SHA-256").digest(value.toByteArray(Charsets.UTF_8))
  return digest.take(8).joinToString("") { "%02x".format(it) }
}

data class SharedResources(
  val bazeliskBinary: Path,
  val registryDirectories: List<Path>,
  val repoCacheDirectory: Path,
  val repoContentsCacheDirectory: Path,
  val diskCacheDirectory: Path,
  val bazeliskHomeDirectory: Path,
)

@Throws(IOException::class)
private fun createResources(cwd: Path, options: WorkerOptions): SharedResources {
  require(options.registryFilesList.isNotEmpty())
  require(options.bazelisk.isNotBlank())

  // every archive is a registry of its own, the list order defines the lookup precedence
  val registryDirectories = options.registryFilesList.mapIndexed { index, file ->
    val directory = Files.createDirectories(cwd.resolve("registries").resolve(index.toString()))
    unzip(Path.of(file), directory, stripPrefix = 1)

    directory
  }

  val repoCacheDirectory = options.repoCache.takeIf { it.isNotBlank() }
    ?.let(::resolvePath)
    ?: cwd.resolve("repo_cache")

  return SharedResources(
    bazeliskBinary = Path.of(options.bazelisk).toAbsolutePath(),
    registryDirectories = registryDirectories,
    repoCacheDirectory = Files.createDirectories(repoCacheDirectory),
    repoContentsCacheDirectory = Files.createDirectories(cwd.resolve("repo_contents_cache")),
    diskCacheDirectory = Files.createDirectories(cwd.resolve("disk_cache")),
    bazeliskHomeDirectory = Files.createDirectories(cwd.resolve("bazelisk_home")),
  )
}

data class BazelServer(
  val identifier: String,
  val version: String,
  val sharedResources: SharedResources,
  val root: Path,
  val outputRootDirectory: Path,
  val outputBaseDirectory: Path,
  val logger: Logger,
)

@Throws(IOException::class)
private fun createServer(logger: Logger, cwd: Path, version: String, slot: Int, shared: SharedResources): BazelServer {
  val root = Files.createDirectories(cwd.resolve("server_${sanitizeVersion(version)}_$slot")).toAbsolutePath()

  return BazelServer(
    identifier = "$version#$slot",
    version = version,
    sharedResources = shared,
    root = root,
    outputRootDirectory = Files.createDirectories(root.resolve("output_root")),
    outputBaseDirectory = Files.createDirectories(root.resolve("output_base")),
    logger = logger.child(name = "$version#$slot"),
  ).also { it.logger.log("created") }
}

/** Makes [version] safe to use as a single path segment. */
private fun sanitizeVersion(version: String): String {
  return version.map { if (it.isLetterOrDigit() || it == '.' || it == '-') it else '_' }.joinToString("")
}

private class ServerPool(private val version: String, maxServers: Int) {
  private val semaphore = Semaphore(maxServers)
  private val available = ConcurrentLinkedQueue<BazelServer>()
  private val slots = AtomicInteger(0)

  suspend fun acquireOrCreate(create: (String, Int) -> BazelServer): BazelServer {
    semaphore.acquire()
    return available.poll() ?: create(version, slots.getAndIncrement())
  }

  fun release(server: BazelServer) {
    available.add(server)
    semaphore.release()
  }

  /** Unsafe access to all currently available servers. */
  fun peakAvailable(): Iterable<BazelServer> = available
}

@Throws(IOException::class)
private fun BazelServer.shutdown() {
  val cmd = mutableListOf<String>()
  cmd.add(sharedResources.bazeliskBinary.toAbsolutePath().toString())
  cmd.add("--output_user_root=$outputRootDirectory")
  cmd.add("--output_base=$outputBaseDirectory")
  cmd.add("shutdown")

  val process = ProcessBuilder(cmd)
    .directory(outputBaseDirectory.toFile())
    .redirectErrorStream(true)
    .apply {
      environment()["USE_BAZEL_VERSION"] = version
      environment()["BAZELISK_HOME"] = sharedResources.bazeliskHomeDirectory.toString()
    }
    .start()

  if (process.waitFor() != 0) {
    process.inputStream.transferTo(System.err)
  }

  // remove the server directory
  deleteRecursive(root)
}
