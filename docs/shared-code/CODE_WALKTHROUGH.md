# Shared code — code walkthrough

[← All features](../README.md) · [How it works](FUNCTIONALITY.md) · [Setup](INSTALL.md)

```
ledger-mru-mobile/
├─ app/                → ledger-mru-flutter, at a precise release
├─ lib, test, assets   → links into app/
├─ pubspec.yaml, .lock → links into app/
└─ android/            → the Android project
```

Flutter wants `pubspec.yaml`, `lib/` and `android/` side by side: symbolic links allow it without copying.

The version number comes from the shared `pubspec.yaml`: mobile X.Y.Z always contains flutter X.Y.Z.
