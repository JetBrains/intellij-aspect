# Copyright 2024 Google Inc.  All rights reserved.
#
# Use of this source code is governed by a BSD-style
# license that can be found in the LICENSE file or at
# https://developers.google.com/open-source/licenses/bsd
#
def _remove_repo(file):
    # https://github.com/protocolbuffers/protobuf/blob/cbaf01ac1604e4bcb12552ca3b52fecd21f3e01b/bazel/common/proto_common.bzl#L48
    """Removes `../repo/` prefix from path, e.g. `../repo/package/path -> package/path`"""
    short_path = file.short_path
    workspace_root = file.owner.workspace_root
    if workspace_root:
        if workspace_root.startswith("external/"):
            workspace_root = "../" + workspace_root.removeprefix("external/")
        return short_path.removeprefix(workspace_root + "/")
    return short_path

def fallback_get_import_path(proto_file):
    # Fall-back code taken from
    # https://github.com/protocolbuffers/protobuf/blob/cbaf01ac1604e4bcb12552ca3b52fecd21f3e01b/bazel/common/proto_common.bzl#L58
    repo_path = _remove_repo(proto_file)
    index = repo_path.find("_virtual_imports/")
    if index >= 0:
        index = repo_path.find("/", index + len("_virtual_imports/"))
        repo_path = repo_path[index + 1:]
    return repo_path
