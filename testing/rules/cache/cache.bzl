load("//private:bazel_rules.bzl", "BazelBinary")
load("//testing/rules/lib:config.bzl", "TestMatrix", "serialize_test_matrix")

def _repo_cache_impl(ctx):
    matrix = ctx.attr.config[TestMatrix]
    output = ctx.actions.declare_file(ctx.label.name + ".zip")

    input = proto.encode_text(struct(
        output_archive = output.path,
        project_archive = ctx.file.project.path,
        configs = serialize_test_matrix(matrix),
    ))

    ctx.actions.run(
        inputs = [it.executable for it in matrix.bazel_binaries] + [ctx.file.project],
        outputs = [output],
        executable = ctx.executable._builder,
        arguments = [input],
        mnemonic = "RepoCache",
        progress_message = "Building repository cache for %{label}",
        use_default_shell_env = True,
    )

    return [DefaultInfo(files = depset([output]))]

repo_cache = rule(
    attrs = {
        "project": attr.label(
            allow_single_file = [".zip"],
            mandatory = True,
        ),
        "config": attr.label(
            providers = [TestMatrix],
            mandatory = True,
        ),
        "_builder": attr.label(
            cfg = "exec",
            executable = True,
            default = Label("//testing/rules/cache:builder_bin"),
        ),
    },
    implementation = _repo_cache_impl,
)
