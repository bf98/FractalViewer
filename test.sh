#!/usr/bin/env sh
set -eu

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" >/dev/null 2>&1 && pwd)
exec "$SCRIPT_DIR/gradlew" test "$@"
