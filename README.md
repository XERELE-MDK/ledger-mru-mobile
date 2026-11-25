# Ledger MRU — Android

[![CI](https://github.com/XERELE-MDK/ledger-mru-mobile/actions/workflows/ci.yml/badge.svg)](https://github.com/XERELE-MDK/ledger-mru-mobile/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/v/release/XERELE-MDK/ledger-mru-mobile)](https://github.com/XERELE-MDK/ledger-mru-mobile/releases/latest)
[![Licence: GPL-3.0](https://img.shields.io/badge/licence-GPL--3.0-blue)](LICENSE)

**Personal bookkeeping in Mauritanian ouguiya (MRU), on your phone.** Real double-entry accounting behind a Simple mode. Android 7.0 or newer.

No account, no server, and **no Internet permission**: the app cannot send your data anywhere.

<p>
  <img src="https://raw.githubusercontent.com/XERELE-MDK/ledger-mru-flutter/main/docs/images/overview.png" width="180" alt="Overview home screen">
  <img src="https://raw.githubusercontent.com/XERELE-MDK/ledger-mru-flutter/main/docs/images/editorial.png" width="180" alt="Editorial home screen">
  <img src="https://raw.githubusercontent.com/XERELE-MDK/ledger-mru-flutter/main/docs/images/pebbles.png" width="180" alt="Pebbles home screen">
  <img src="https://raw.githubusercontent.com/XERELE-MDK/ledger-mru-flutter/main/docs/images/opening-balances.png" width="180" alt="Opening balances">
</p>

## Download

[**Latest release**](https://github.com/XERELE-MDK/ledger-mru-mobile/releases/latest):

| APK | For |
|---|---|
| `…-android-arm64-v8a.apk` | **almost every phone since 2017 — take this one** |
| `…-android-armeabi-v7a.apk` | older 32-bit phones |
| `…-android-x86_64.apk` | emulators, Chromebooks |
| `…-android-universal.apk` | all three in one, larger |

Open the APK on the phone and allow installing from your browser or file manager. Check a download: `sha256sum -c SHA256SUMS --ignore-missing`.

⚠️ The universal APK cannot be installed **over** a per-processor one. Export your data before switching.

## What you get

- **Simple or Accountant mode**: say "Expense, 300, taxi, cash" or pick the debit and credit yourself.
- **Journal, Ledger, Reports**: the month by day, one account with its running balance, trial balance, 6-month comparison, budgets.
- **Opening balances**, **monthly budgets**, **demo data** to try before starting clean.
- **4 home screens**: Overview, Editorial, T-account, Pebbles.
- **6 themes**: Organic (light / dark), Cappuccino, AMOLED Black, Arctic, Sahara, Sakura — each with its own fonts.
- **3 languages**: English, Français, 日本語 — amounts and dates follow.
- **Sync** with your PC through a folder shared by [Syncthing](https://syncthing.net), no server.
- **Import / export** of the whole book as JSON.

Every feature is documented in [ledger-mru-flutter/docs](https://github.com/XERELE-MDK/ledger-mru-flutter/tree/main/docs#documentation), where the code lives.

## Privacy

| | |
|---|---|
| Network | none: the release APK declares **no permission at all**, and CI checks it |
| Account, ads, analytics | none |
| Where data lives | the app's private storage, plain JSON; plus the export file and sync folder you choose |
| Backup | Android may include the data in your Google backup if you turned it on |

## The Ledger MRU family

| Repository | What | For |
|---|---|---|
| [ledger-mru-mobile](https://github.com/XERELE-MDK/ledger-mru-mobile) (here) | Android project | phone |
| [ledger-mru-flutter](https://github.com/XERELE-MDK/ledger-mru-flutter) | Dart: screens, logic, tests, Linux build | Linux PC, same app as the phone |
| [ledger-mru-rust](https://github.com/XERELE-MDK/ledger-mru-rust) | Rust + Slint | Linux PC, lightweight |

All three read and write **the same JSON file** and sync through a shared folder.

## How this repository is built

The Dart code is **not copied here**: `app/` is a git submodule pinned to a release of [ledger-mru-flutter](https://github.com/XERELE-MDK/ledger-mru-flutter). `lib`, `test`, `assets` and `pubspec.yaml` are links into it. This repository only adds `android/` (Kotlin channels for the file picker and the shared folder, icon, signing).

```bash
git clone --recursive https://github.com/XERELE-MDK/ledger-mru-mobile.git
cd ledger-mru-mobile
tools/build_apks.sh          # or: flutter build apk --release --split-per-abi
```

Without `--recursive`: `git submodule update --init`. On Windows, enable symbolic links (developer mode, `git config core.symlinks true`).

## Documentation

What is specific to Android, in [**docs/**](docs/README.md): [shared code](docs/shared-code/FUNCTIONALITY.md) · [file picker](docs/file-picker/FUNCTIONALITY.md) · [shared folder](docs/shared-folder/FUNCTIONALITY.md) · [icon](docs/icon/FUNCTIONALITY.md) · [privacy](docs/privacy/FUNCTIONALITY.md) · [APKs and signing](docs/release/FUNCTIONALITY.md). Also: [Changelog](CHANGELOG.md) · [Versioning](docs/VERSIONING.md).

## Licence

[GPL-3.0](LICENSE).
