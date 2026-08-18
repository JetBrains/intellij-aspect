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

def _module_container_impl(ctx):
    return []

_container = rule(
    implementation = _module_container_impl,
    doc = "Empty target that acts as a container for module providers.",
)

def _aspect(provider, implementation, field = None, attrs = None):
    """Creates a module aspcet that contributes the module's provider to the container."""

    def aspect_impl(container, actx):
        attr = {name: getattr(actx.attr, name) for name in list(attrs or [])}

        def capture(target, ctx):
            return implementation(target, ctx, attr)

        return [provider(field = field, func = capture)]

    return aspect(
        implementation = aspect_impl,
        provides = [provider],
        attrs = attrs or {},
    )

def _define(file, aspect, rulesets = None, fragments = None, toolchains = None, aspect_providers = None):
    """Creates a struct that descirbes the requirements for this module."""
    return struct(
        file = file,
        aspect = aspect,
        rulesets = rulesets or [],
        fragments = fragments or [],
        toolchains = toolchains or [],
        aspect_providers = aspect_providers or [],
    )

def _result(value, *, internal_value = None, outputs = None, dependencies = None):
    """Creates the result struct of a module."""
    return struct(
        value = value,
        internal_value = internal_value or struct(),
        outputs = outputs or {},
        dependencies = dependencies or {},
    )

intellij_module = struct(
    container = _container,
    aspect = _aspect,
    define = _define,
    result = _result,
)
