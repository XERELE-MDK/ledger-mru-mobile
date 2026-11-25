# APK et signature — mise en place

[← Toutes les fonctionnalités](../README.md) · [Fonctionnement](FUNCTIONALITY.md) · [Parcours du code](CODE_WALKTHROUGH.md)

## Compiler

```bash
git submodule update --init
tools/build_apks.sh
```

## Utiliser une vraie clé

Par défaut, la clé de **debug** de la machine signe. Pour une clé dédiée, créer `android/key.properties` (ignoré par git) :

```properties
storeFile=/chemin/vers/ledger.jks
storePassword=…
keyAlias=ledger
keyPassword=…
```

⚠️ Changer de clé oblige à désinstaller l'app : Android refuse une mise à jour signée autrement. **Sauvegarder la clé** : la perdre empêche toute mise à jour.
