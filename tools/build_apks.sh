#!/usr/bin/env bash
# Builds the four release APKs into dist/, checks them, and writes SHA256SUMS.
#   android-arm64-v8a    almost every phone since ~2017
#   android-armeabi-v7a  old or very cheap 32-bit phones
#   android-x86_64       emulators, Chromebooks
#   android-universal    all three in one
# Usage: tools/build_apks.sh [--no-build]
set -euo pipefail
cd "$(dirname "$0")/.."
ver=$(grep -m1 '^version:' pubspec.yaml | sed 's/version: *//; s/+.*//')
out=build/app/outputs/flutter-apk
if [[ ${1:-} != --no-build ]]; then
  flutter build apk --release --split-per-abi
  flutter build apk --release
fi
rm -rf dist; mkdir -p dist
for abi in arm64-v8a armeabi-v7a x86_64 universal; do
  src=$out/app-$abi-release.apk; [[ $abi == universal ]] && src=$out/app-release.apk
  cp "$src" "dist/ledger-mru-$ver-android-$abi.apk"
done
sdk=${ANDROID_HOME:-$HOME/Android/Sdk}
bt=$(ls -d "$sdk"/build-tools/* 2>/dev/null | sort -V | tail -1)
for a in dist/*.apk; do
  "$bt/apksigner" verify "$a"
  badging=$("$bt/aapt2" dump badging "$a" 2>/dev/null || true)
  [[ $badging == *"versionName='$ver'"* ]] || { echo "$a: wrong version" >&2; exit 1; }
  perms=$("$bt/aapt2" dump permissions "$a" 2>/dev/null || true)
  [[ $perms != *android.permission.INTERNET* ]] || { echo "$a: asks for Internet" >&2; exit 1; }
done
(cd dist && sha256sum -- *.apk > SHA256SUMS)
"$bt/apksigner" verify --print-certs dist/*-universal.apk 2>/dev/null | grep -E 'DN|SHA-256' | head -2
ls -lh dist
