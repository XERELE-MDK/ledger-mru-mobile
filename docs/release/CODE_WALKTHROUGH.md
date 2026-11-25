# APK et signature — parcours du code

[← Toutes les fonctionnalités](../README.md) · [Fonctionnement](FUNCTIONALITY.md) · [Mise en place](INSTALL.md)

- Numéro interne : `rang du processeur × 1000 + N`, N étant le nombre après le `+` de `pubspec.yaml`.
- `tools/build_apks.sh` compile, vérifie (signature, version, permissions) et écrit `SHA256SUMS`.
- `android/gradle.properties` : mémoire limitée à 2,5 Go, Kotlin dans la même JVM.
- La CI compile à chaque envoi, mais avec une clé **jetable** : ses APK ne servent qu'à vérifier. Ceux des Releases sont signés sur la machine du mainteneur.
