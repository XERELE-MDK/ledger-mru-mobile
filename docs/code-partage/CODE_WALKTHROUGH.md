# Code partagé — parcours du code

[← Toutes les fonctionnalités](../README.md) · [Fonctionnement](FUNCTIONALITY.md) · [Mise en place](INSTALL.md)

```
ledger-mru-mobile/
├─ app/                → ledger-mru-flutter, à une version précise
├─ lib, test, assets   → liens vers app/
├─ pubspec.yaml, .lock → liens vers app/
└─ android/            → le projet Android
```

Flutter exige `pubspec.yaml`, `lib/` et `android/` côte à côte : les liens symboliques le permettent sans copie.

Le numéro de version vient du `pubspec.yaml` partagé : mobile X.Y.Z contient toujours flutter X.Y.Z.
