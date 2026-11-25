# Confidentialité — parcours du code

[← Toutes les fonctionnalités](../README.md) · [Fonctionnement](FUNCTIONALITY.md)

- `AndroidManifest.xml` (release) ne déclare aucune permission. `INTERNET` n'existe que dans les variantes debug et profile, pour le rechargement à chaud.
- `android:allowBackup` n'est pas défini, donc actif. Le mettre à `false` couperait aussi la récupération après un changement de téléphone : choix laissé ouvert.
- `tools/build_apks.sh` refuse un APK qui demanderait `INTERNET`.
