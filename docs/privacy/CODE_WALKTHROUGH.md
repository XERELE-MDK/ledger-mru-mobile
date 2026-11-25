# Privacy — code walkthrough

[← All features](../README.md) · [How it works](FUNCTIONALITY.md)

- The release `AndroidManifest.xml` declares no permission. `INTERNET` only exists in the debug and profile variants, for hot reload.
- `android:allowBackup` is not set, so it is on. Setting it to `false` would also block recovery after switching phones: left open on purpose.
- `tools/build_apks.sh` refuses an APK that would ask for `INTERNET`.
