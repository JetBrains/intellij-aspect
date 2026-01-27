#!/usr/bin/env python3

import base64
import hashlib
import io
import json
import os
import sys
import tarfile

def hash_file(fname):
    with open(fname, "rb") as f:
        return hashlib.file_digest(f, "sha256").hexdigest()

def add_file(out, tar_name, local_name):
    info = tarfile.TarInfo(tar_name)
    with open(local_name, "rb") as f:
        f.seek(0, 2)
        info.size = f.tell()
        f.seek(0)
        out.addfile(info, f)

def add_file_contents(out, tar_name, content):
    info = tarfile.TarInfo(tar_name)
    content_bytes = content.encode('utf-8')
    info.size = len(content_bytes)
    out.addfile(info, io.BytesIO(content_bytes))

def add_distdir(out, dirname, *, url, archive):
    """Extend the tarwriter out by a distdir under the specified dirname

    In other words, add the given archive as a file in that directory, named
    after the basename of the url.
    """
    add_file(out, os.path.join(dirname, os.path.basename(url)), archive)

def add_registry(out, dirname, *, module_name, module_version, module_file, url, archive, archive_digest):
    """ Extend the tarwriter out by a registry in the specified dirname.

    In other words, add a single subdirectory for the one version of the module, containing
    - the given module file, with the version number spliced in, and
    - a file source.json with the very basic information (url, hash, and module_name as strip_prefix)
    """
    entry_dir = os.path.join(dirname, "modules", module_name, module_version)
    with open(module_file) as f:
        module_declaration = f.readlines()
    module_declaration_with_version = [
        'module(name = "intellij_aspect", version = %r, compatibility_level = 1)' % (module_version,)
        if line.startswith("module") else line
        for line in module_declaration
    ]
    add_file_contents(out, os.path.join(entry_dir, "MODULE.bazel"), "\n".join(module_declaration_with_version) + "\n")
    source_desc = {
        "type": "archive",
        "url": url,
        "integrity": "sha256-%s" % (base64.b64encode(bytes.fromhex(archive_digest)).decode('utf-8'),),
        "strip_prefix": module_name,
    }
    add_file_contents(out, os.path.join(entry_dir, "source.json"), json.dumps(source_desc, indent=2))

def generate_local_registry_deployment(*, archive, module_file, output, module_version, module_name):
    url = "http://nonexistent.example.com/%s/%s-%s.tar.gz" % (module_name, module_name, module_version)
    with open(output, "wb") as f:
        out = tarfile.open(mode="w", fileobj =f)
        digest = hash_file(archive)
        add_registry(out, ".ijaspect/registry", module_name=module_name, module_version=module_version,
                     module_file=module_file, url=url, archive=archive, archive_digest = digest)

        # While repo-cache is the more modern approach, the option `--repository_cache` does not accumulate,
        # hence a value set in the user's ~/.bazelrc will invalidate any value set in the project's .bazelrc;
        # `--distdir`, on the other hand, properly accumulates.
        add_distdir(out, ".ijaspect/distdir", url=url, archive=archive)
        out.close()

if __name__ == "__main__":
    generate_local_registry_deployment(
        archive = sys.argv[1],
        module_file = sys.argv[2],
        output = sys.argv[3],
        module_version = sys.argv[4],
        module_name = sys.argv[5],
    )