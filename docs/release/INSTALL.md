# APKs and signing — setup

[← All features](../README.md) · [How it works](FUNCTIONALITY.md) · [Code walkthrough](CODE_WALKTHROUGH.md)

## Build

```bash
git submodule update --init
tools/build_apks.sh
```

## Using a real key

By default the machine's **debug** key signs. For a dedicated key, create `android/key.properties` (ignored by git):

```properties
storeFile=/path/to/ledger.jks
storePassword=…
keyAlias=ledger
keyPassword=…
```

⚠️ Changing keys forces an uninstall: Android refuses an update signed differently. **Back up the key**: losing it blocks every future update.
