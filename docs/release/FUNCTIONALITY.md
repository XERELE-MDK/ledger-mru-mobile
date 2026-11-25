# APKs and signing — how it works

[← All features](../README.md) · [Code walkthrough](CODE_WALKTHROUGH.md) · [Setup](INSTALL.md)

| APK | Phones |
|---|---|
| `arm64-v8a` | almost all since 2017 |
| `armeabi-v7a` | older, 32-bit |
| `x86_64` | emulators, Chromebooks |
| `universal` | all three together, larger |

Android 7.0 or newer.

⚠️ The universal APK cannot be installed **over** a per-processor APK: its internal number is lower. Export your data before uninstalling.
