#!/usr/bin/env bash
set -euo pipefail

PROPS_FILE="local.properties"

if [[ ! -f "$PROPS_FILE" ]]; then
  echo "Missing $PROPS_FILE"
  exit 1
fi

get_prop() {
  local key="$1"
  # Reads KEY=VALUE, then extracts VALUE:
  local line
  line="$(grep -E "^${key}=" "$PROPS_FILE" | tail -n 1 || true)"
  [[ -n "$line" ]] || return 1
  echo "${line#*=}" | tr -d '\r'
}

export ORG_GRADLE_PROJECT_mavenCentralUsername="$(get_prop mavenCentralUsername)"
export ORG_GRADLE_PROJECT_mavenCentralPassword="$(get_prop mavenCentralPassword)"

./gradlew publishToMavenCentral
