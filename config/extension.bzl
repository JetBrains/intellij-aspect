# Copyright 2026 JetBrains s.r.o.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

def _config_repo_impl(rctx):
    substitutions = {
        "{BAZEL_VERSION}": native.bazel_version,
    }

    rctx.file("BUILD", "")
    rctx.template("config.bzl", rctx.attr._config_template, substitutions = substitutions)

config_repo = repository_rule(
    implementation = _config_repo_impl,
    local = True,  # force reruns on server restarts to keep native.bazel_version up-to-date.
    attrs = {
        "_config_template": attr.label(
            allow_single_file = True,
            default = Label(":config.tpl"),
        ),
    },
)

def _config_extension_impl(mctx):
    config_repo(name = "intellij_config")

config = module_extension(
    implementation = _config_extension_impl,
)
