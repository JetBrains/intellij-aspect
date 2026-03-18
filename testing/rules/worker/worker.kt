package com.intellij.aspect.testing.rules.worker

import com.intellij.aspect.private.lib.utils.parseTextProto
import com.intellij.aspect.private.lib.utils.tee
import com.intellij.aspect.private.lib.utils.unzip
import com.intellij.aspect.testing.rules.worker.WorkerProto.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively

fun worker(
  args: Array<String>,
  body: Sandbox.(WorkArguments) -> Unit,
) = runBlocking {
  require(args.contains("--persistent_worker"))

  // create persisted temp directory outside the sandbox and execroot
  val cwd = Files.createTempDirectory("bazel_worker_").toAbsolutePath()

  log("worker started in: $cwd")

  // the global worker configuration
  val options = parseTextProto<WorkerOptions>(args[0])
  val shared = createResources(cwd, options)

  // keep track of fixed number of bazel servers per version
  val pools = mutableMapOf<String, ServerPool>()

  while (true) {
    val request = WorkRequest.parseDelimitedFrom(System.`in`) ?: break
    val input = parseTextProto<WorkArguments>(request.argumentsList.joinToString(separator = "\n"))

    val pool = pools.getOrPut(input.config.bazelVersion) { ServerPool(input.config.bazelVersion) }

    launch(Dispatchers.IO) {
      val server = pool.acquireOrCreate { createServer(cwd, input.config.bazelVersion, shared) }
      val sandbox = Files.createTempDirectory(cwd, "sandbox_").toAbsolutePath()

      val stdout = ByteArrayOutputStream()
      val stderr = ByteArrayOutputStream()

      try {
        Sandbox(
          server = server,
          sandboxRoot = sandbox,
          stdout = tee(stdout, stderr),
          stderr = stderr,
        ).use { ctx -> body(ctx, input) }

        if (request.verbosity > 0) {
          System.err.write(stderr.toByteArray())
        }

        synchronized(System.out) {
          WorkResponse.newBuilder()
            .setExitCode(0)
            .setRequestId(request.requestId)
            .setOutput(stdout.toString())
            .build()
            .writeDelimitedTo(System.out)
        }
      } catch (e: Throwable) {
        val builder = StringBuilder()
        builder.appendLine(stderr.toString())
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

        try {
          @OptIn(ExperimentalPathApi::class)
          sandbox.deleteRecursively()
        } catch (_: IOException) {
          // best effort cleanup during
        }
      }
    }
  }

  try {
    @OptIn(ExperimentalPathApi::class)
    cwd.deleteRecursively()
  } catch (_: IOException) {
    // best effort cleanup during
  }
}

data class SharedResources(
  val bazeliskBinary: Path,
  val registryDirectory: Path,
  val repoCacheDirectory: Path,
  val diskCacheDirectory: Path,
  val bazeliskHomeDirectory: Path,
)

@Throws(IOException::class)
private fun createResources(cwd: Path, options: WorkerOptions): SharedResources {
  require(options.registryFile.isNotBlank())
  require(options.bazelisk.isNotBlank())

  val registryDirectory = Files.createDirectories(cwd.resolve("registry"))
  unzip(Path.of(options.registryFile), registryDirectory, stripPrefix = 1)

  return SharedResources(
    bazeliskBinary = Path.of(options.bazelisk).toAbsolutePath(),
    registryDirectory = registryDirectory,
    repoCacheDirectory = Files.createDirectories(cwd.resolve("repo_cache")),
    diskCacheDirectory = Files.createDirectories(cwd.resolve("disk_cache")),
    bazeliskHomeDirectory = Files.createDirectories(cwd.resolve("bazelisk_home")),
  )
}

data class BazelServer(
  val version: String,
  val sharedResources: SharedResources,
  val outputRootDirectory: Path,
  val outputBaseDirectory: Path,
)

@Throws(IOException::class)
private fun createServer(cwd: Path, version: String, shared: SharedResources): BazelServer {
  log("creating new server for: $version")

  val root = Files.createTempDirectory(cwd, "server_").toAbsolutePath()

  return BazelServer(
    version = version,
    sharedResources = shared,
    outputRootDirectory = Files.createDirectories(root.resolve("output_root")),
    outputBaseDirectory = Files.createDirectories(root.resolve("output_base")),
  )
}

private class ServerPool(private val version: String) {
  private val semaphore = Semaphore(3)
  private val available = ConcurrentLinkedQueue<BazelServer>()

  suspend fun acquireOrCreate(create: (String) -> BazelServer): BazelServer {
    semaphore.acquire()
    return available.poll() ?: create(version)
  }

  fun release(server: BazelServer) {
    available.add(server)
    semaphore.release()
  }
}

private fun log(message: String) {
  val time = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)
  System.err.println("[$time] $message")
}