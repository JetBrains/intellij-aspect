#!/usr/bin/env bash
#
# Initializes a development workspace:
#   1. downloads the latest bazelisk release and installs it as `bazel` (and `bazelisk`)
#   2. configures user.bazelrc to run all build actions inside a docker sandbox
#
# Environment overrides:
#   INSTALL_DIR   where to install the binaries (default: ~/.local/bin)
#   DOCKER_IMAGE  image used for the docker sandbox (default: ubuntu:latest)

set -euo pipefail

INSTALL_DIR="${INSTALL_DIR:-$HOME/.local/bin}"
DOCKER_IMAGE="${DOCKER_IMAGE:-ubuntu:latest}"

WORKSPACE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
USER_BAZELRC="$WORKSPACE_DIR/user.bazelrc"

BLOCK_BEGIN="# >>> init_workspace.sh: docker sandbox >>>"
BLOCK_END="# <<< init_workspace.sh: docker sandbox <<<"

log() {
  echo "[init_workspace] $*" >&2
}

detect_platform() {
  local os arch

  case "$(uname -s)" in
    Linux) os="linux" ;;
    Darwin) os="darwin" ;;
    *) log "unsupported operating system: $(uname -s)"; exit 1 ;;
  esac

  case "$(uname -m)" in
    x86_64 | amd64) arch="amd64" ;;
    aarch64 | arm64) arch="arm64" ;;
    *) log "unsupported architecture: $(uname -m)"; exit 1 ;;
  esac

  echo "$os-$arch"
}

install_bazelisk() {
  local platform version url tmp

  platform="$(detect_platform)"

  # resolve the latest release tag by following the GitHub redirect
  version="$(curl -fsSLI -o /dev/null -w '%{url_effective}' https://github.com/bazelbuild/bazelisk/releases/latest)"
  version="${version##*/}"
  url="https://github.com/bazelbuild/bazelisk/releases/download/$version/bazelisk-$platform"

  log "downloading bazelisk $version ($platform)"
  tmp="$(mktemp)"
  trap 'rm -f "$tmp"' RETURN
  curl -fsSL --retry 3 -o "$tmp" "$url"
  chmod +x "$tmp"

  mkdir -p "$INSTALL_DIR"
  install -m 0755 "$tmp" "$INSTALL_DIR/bazelisk"
  ln -sf bazelisk "$INSTALL_DIR/bazel"
  log "installed bazelisk to $INSTALL_DIR/bazelisk (linked as bazel)"

  case ":$PATH:" in
    *":$INSTALL_DIR:"*) ;;
    *) log "warning: $INSTALL_DIR is not on PATH, add it with: export PATH=\"$INSTALL_DIR:\$PATH\"" ;;
  esac
}

configure_bazelrc() {
  local tmp

  if ! command -v docker > /dev/null || ! docker info > /dev/null 2>&1; then
    log "warning: docker is not available, builds using the docker sandbox will fail"
  fi

  touch "$USER_BAZELRC"

  # replace a previously generated block to keep the script idempotent
  tmp="$(mktemp)"
  awk -v begin="$BLOCK_BEGIN" -v end="$BLOCK_END" '
    $0 == begin { skip = 1; next }
    $0 == end { skip = 0; next }
    !skip { print }
  ' "$USER_BAZELRC" > "$tmp"

  cat >> "$tmp" << EOF
$BLOCK_BEGIN
# run all build actions inside a docker container
build --experimental_enable_docker_sandbox
build --spawn_strategy=docker
build --experimental_docker_image=$DOCKER_IMAGE
$BLOCK_END
EOF

  mv "$tmp" "$USER_BAZELRC"
  log "configured docker sandbox ($DOCKER_IMAGE) in $USER_BAZELRC"
}

install_bazelisk
configure_bazelrc
