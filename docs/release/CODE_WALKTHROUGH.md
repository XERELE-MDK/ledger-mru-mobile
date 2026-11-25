# APKs and signing — code walkthrough

[← All features](../README.md) · [How it works](FUNCTIONALITY.md) · [Setup](INSTALL.md)

- Internal number: `processor rank × 1000 + N`, N being the number after the `+` in `pubspec.yaml`.
- `tools/build_apks.sh` builds, checks (signature, version, permissions) and writes `SHA256SUMS`.
- `android/gradle.properties`: memory capped at 2.5 GB, Kotlin inside the same JVM.
- CI builds on every push, but with a **throwaway** key: its APKs are only for checking. The ones in Releases are signed on the maintainer's machine.
