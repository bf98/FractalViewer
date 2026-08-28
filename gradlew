#!/bin/sh
#
# Gradle Wrapper launcher for POSIX systems.
#

set -eu

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" >/dev/null 2>&1 && pwd)

if [ -n "${JAVA_HOME:-}" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
    if [ ! -x "$JAVACMD" ]; then
        echo "ERROR: JAVA_HOME points to an invalid Java installation: $JAVA_HOME" >&2
        exit 1
    fi
else
    JAVACMD=java
    if ! command -v "$JAVACMD" >/dev/null 2>&1; then
        echo "ERROR: JAVA_HOME is not set and java was not found in PATH." >&2
        exit 1
    fi
fi

exec "$JAVACMD" \
    -Dorg.gradle.appname=gradlew \
    -jar "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" \
    "$@"
