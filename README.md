# Ledger MRU — Mobile (Android)

[![CI](https://github.com/XERELE-MDK/ledger-mru-mobile/actions/workflows/ci.yml/badge.svg)](https://github.com/XERELE-MDK/ledger-mru-mobile/actions/workflows/ci.yml)

Comptabilité personnelle en ouguiya (MRU) pour **Android 7.0 et plus**. Aucun compte, aucun serveur, et **aucune permission Internet**.

| Dépôt | Contenu | Pour |
|---|---|---|
| [ledger-mru-rust](https://github.com/XERELE-MDK/ledger-mru-rust) | Rust + Slint | PC Linux, léger (~25 Mio de mémoire) |
| [ledger-mru-flutter](https://github.com/XERELE-MDK/ledger-mru-flutter) | Dart : écrans, logique, tests, version Linux | PC Linux, même app que le téléphone |
| [ledger-mru-mobile](https://github.com/XERELE-MDK/ledger-mru-mobile) | projet Android | téléphone |

Les trois lisent et écrivent **le même fichier JSON** et se synchronisent par un dossier partagé, sans serveur.

## Comment ce dépôt est fait

Le code Dart n'est **pas copié ici** : `app/` est un sous-module git qui pointe vers une version précise de [ledger-mru-flutter](https://github.com/XERELE-MDK/ledger-mru-flutter). `lib`, `test`, `assets` et `pubspec.yaml` sont des liens vers ce sous-module. Ce dépôt n'apporte que `android/`.

```bash
git clone --recursive https://github.com/XERELE-MDK/ledger-mru-mobile.git
cd ledger-mru-mobile
flutter build apk --release --split-per-abi
```

Sans `--recursive` : `git submodule update --init`. Sous Windows, activer les liens symboliques (`git config core.symlinks true`, mode développeur).

APK prêts à installer : [Releases](https://github.com/XERELE-MDK/ledger-mru-mobile/releases). Presque tous les téléphones : `arm64-v8a`.

## Licence

[GPL-3.0](LICENSE).
