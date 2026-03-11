load(":artifact_location.bzl", "artifact_location")
load(":common.bzl", "intellij_common")

def _target_hash(target):
    """Creates a unique hash for the target based on its key."""
    key = target[intellij_common.TargetInfo].key
    parts = [key.label, getattr(key, "configuration", "")] + key.aspect_ids
    return hash(".".join(parts))

def _write_info(target, ctx, fields):
    """
    Collects some common information in addtion to the procided fields and
    writes everything to an intellij-info.txt file.
    """

    build_file_location = artifact_location.create(
        root_path = ctx.label.workspace_root,
        relative_path = ctx.label.package + "/BUILD" if ctx.label.package else "BUILD",
        is_source = True,
        is_external = intellij_common.label_is_external(ctx.label),
    )

    info = fields | {
        "build_file_artifact_location": build_file_location,
        "features": ctx.features,
        "kind_string": ctx.rule.kind,
        "tags": ctx.rule.attr.tags,
        "key": target[intellij_common.TargetInfo].key,
    }

    # bazel allows target names differing only by case, so append a hash to support case-insensitive file systems
    file_name = "%s-%s.intellij-info.txt" % (target.label.name, _target_hash(target))

    file = ctx.actions.declare_file(file_name)
    ctx.actions.write(file, proto.encode_text(struct(**info)))

    return file

def _write_toolchain_info(target, ctx, name, info):
    """Convenience wrapper around write ide info for toolchains."""
    return _write_info(target, ctx, {name: info})

ide_info = struct(
    write = _write_info,
    write_toolchain = _write_toolchain_info,
)
