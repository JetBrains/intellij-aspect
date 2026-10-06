#!/usr/bin/env bash
# Air cloud environment startup for intellij-aspect (Bazel).
#
# Installs, in userspace (no sudo available):
#   - bazelisk as ~/.local/bin/bazel (picks up .bazelversion)
#   - a GCC 13 / binutils toolchain unpacked from the Ubuntu packages into
#     ~/.local/toolchain, exposed through wrappers in ~/.local/toolchain/bin
#     (rules_cc's autoconfigured toolchain needs gcc on PATH)
# On a WARMUP run it also builds //... and runs a test smoke so the Bazel
# repository/disk caches and downloaded Bazel versions end up in the snapshot.
set -euo pipefail

if [ "${AIR_STARTUP_MODE:-}" = warmup ]; then WARMUP=1; else WARMUP=; fi

REPO_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
BIN_DIR="$HOME/.local/bin"
TC_DIR="$HOME/.local/toolchain"
BAZELISK_VERSION=1.25.0 # keep in sync with bazel_registry.bazelisk in MODULE.bazel

log() { echo "[startup] $*"; }

install_bazelisk() {
  if [ -x "$BIN_DIR/bazel" ] && "$BIN_DIR/bazel" version --gnu_format >/dev/null 2>&1 </dev/null; then
    log "bazelisk already installed"
    return
  fi
  log "installing bazelisk $BAZELISK_VERSION"
  mkdir -p "$BIN_DIR"
  curl -fsSL --retry 3 -o "$BIN_DIR/bazel.tmp" \
    "https://github.com/bazelbuild/bazelisk/releases/download/v$BAZELISK_VERSION/bazelisk-linux-amd64"
  chmod +x "$BIN_DIR/bazel.tmp"
  mv "$BIN_DIR/bazel.tmp" "$BIN_DIR/bazel"
  ln -sf bazel "$BIN_DIR/bazelisk"
}

toolchain_works() {
  [ -x "$TC_DIR/bin/gcc" ] || return 1
  local tmp
  tmp="$(mktemp -d)"
  printf '#include <iostream>\nint main(){std::cout<<"ok";}\n' >"$tmp/t.cc"
  "$TC_DIR/bin/g++" "$tmp/t.cc" -o "$tmp/t" >/dev/null 2>&1 && [ "$("$tmp/t")" = ok ]
  local rc=$?
  rm -rf "$tmp"
  return $rc
}

download_toolchain_debs() {
  local apt_opts=("$@") pkgs
  # resolve the full dependency closure; packages already installed on the host are added explicitly
  # since the sysroot needs their development files and shared objects as well
  pkgs="$(apt-get "${apt_opts[@]}" -s install --no-install-recommends gcc g++ binutils libc6-dev 2>/dev/null |
    awk '/^Inst /{print $2}' | tr '\n' ' ')"
  [ -n "$pkgs" ] || return 1
  # shellcheck disable=SC2086
  apt-get "${apt_opts[@]}" download $pkgs libc6 libgcc-s1 libstdc++6 libatomic1 libgmp10 libzstd1 zlib1g libjansson4
}

install_toolchain() {
  if toolchain_works; then
    log "gcc toolchain already installed"
    return
  fi
  log "installing userspace gcc toolchain into $TC_DIR"
  rm -rf "$TC_DIR"
  mkdir -p "$TC_DIR/debs" "$TC_DIR/root" "$TC_DIR/hostlib" "$TC_DIR/bin"

  if ! (cd "$TC_DIR/debs" && download_toolchain_debs); then
    log "image apt lists are stale, refreshing a private copy"
    rm -f "$TC_DIR"/debs/*.deb
    local apt_dir="$TC_DIR/apt"
    mkdir -p "$apt_dir/lists/partial" "$apt_dir/cache/archives/partial"
    local apt_opts=(-o "Dir::State::Lists=$apt_dir/lists" -o "Dir::Cache=$apt_dir/cache"
      -o Debug::NoLocking=1 -o "APT::Sandbox::User=$(id -un)")
    apt-get "${apt_opts[@]}" update
    (cd "$TC_DIR/debs" && download_toolchain_debs "${apt_opts[@]}")
  fi

  local deb
  for deb in "$TC_DIR"/debs/*.deb; do dpkg-deb -x "$deb" "$TC_DIR/root"; done
  rm -rf "$TC_DIR/debs" "$TC_DIR/apt"

  local root="$TC_DIR/root"
  # usrmerge layout, the linker scripts in the sysroot reference /lib/...
  ln -sfn usr/lib "$root/lib"
  [ -d "$root/usr/lib64" ] && ln -sfn usr/lib64 "$root/lib64"

  # shared libraries of cc1/as/ld that are not installed on the host
  local p l
  for p in isl mpc mpfr bfd ctf sframe opcodes gprofng; do
    for l in "$root"/usr/lib/x86_64-linux-gnu/lib"$p"*.so*; do
      [ -e "$l" ] && ln -sfn "$l" "$TC_DIR/hostlib/"
    done
  done

  # wrappers use absolute paths, Bazel actions may run without HOME
  local t tgt
  for t in gcc g++ cpp; do
    cat >"$TC_DIR/bin/$t" <<EOF
#!/bin/sh
export LD_LIBRARY_PATH="$TC_DIR/hostlib\${LD_LIBRARY_PATH:+:\$LD_LIBRARY_PATH}"
exec "$root/usr/bin/x86_64-linux-gnu-$t-13" --sysroot="$root" -B"$root/usr/bin/" "\$@"
EOF
  done
  for t in ar as ld ld.bfd ld.gold nm objcopy objdump strip ranlib readelf addr2line c++filt dwp size strings gcov; do
    tgt="$root/usr/bin/x86_64-linux-gnu-$t"
    [ -e "$tgt" ] || tgt="$root/usr/bin/$t"
    cat >"$TC_DIR/bin/$t" <<EOF
#!/bin/sh
export LD_LIBRARY_PATH="$TC_DIR/hostlib\${LD_LIBRARY_PATH:+:\$LD_LIBRARY_PATH}"
exec "$tgt" "\$@"
EOF
  done
  ln -sf gcc "$TC_DIR/bin/cc"
  ln -sf g++ "$TC_DIR/bin/c++"
  chmod +x "$TC_DIR"/bin/*

  toolchain_works || { log "ERROR: installed gcc toolchain cannot compile a C++ program"; return 1; }
  log "gcc toolchain installed: $("$TC_DIR/bin/gcc" --version | head -1)"
}

install_shell_env() {
  local env_file="$HOME/.intellij-aspect-env.sh" marker="# intellij-aspect env"
  cat >"$env_file" <<EOF
case ":\$PATH:" in *":$BIN_DIR:"*) ;; *) export PATH="$BIN_DIR:\$PATH" ;; esac
case ":\$PATH:" in *":$TC_DIR/bin:"*) ;; *) export PATH="$TC_DIR/bin:\$PATH" ;; esac
EOF
  local profile=""
  for f in "$HOME/.bash_profile" "$HOME/.bash_login" "$HOME/.profile"; do
    if [ -f "$f" ]; then profile="$f"; break; fi
  done
  [ -n "$profile" ] || { profile="$HOME/.profile"; touch "$profile"; }
  touch "$HOME/.bashrc"
  for f in "$profile" "$HOME/.bashrc"; do
    grep -qF "$marker" "$f" || printf '\n%s\n[ -f "%s" ] && . "%s"\n' "$marker" "$env_file" "$env_file" >>"$f"
  done
}

healthcheck() {
  cd "$REPO_DIR"
  # the full //... fixture matrix does not fit the default disk (each fixture config runs a nested
  # Bazel per version, the proto fixtures each extract a ~2GB LLVM), so build everything but the
  # fixtures and run a representative test subset that exercises the nested Bazel 7/8/9 servers
  log "healthcheck: bazel build (all non-fixture targets)"
  bazel build -- //... -//testing/fixtures/... -//testing/tests/...
  log "healthcheck: bazel test (sdk unit tests + java fixture test on all Bazel versions)"
  bazel test --test_output=errors //testing/tests/sdk/... //testing/tests/java:SimpleTest
  log "healthcheck: OK"
}

install_bazelisk
install_toolchain
install_shell_env
export PATH="$TC_DIR/bin:$BIN_DIR:$PATH"

cd "$REPO_DIR"
log "fetching Bazel $(cat .bazelversion)"
bazel version --gnu_format </dev/null

if [ -n "$WARMUP" ]; then healthcheck; fi
log "done"
