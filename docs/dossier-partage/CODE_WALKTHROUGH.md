# Dossier partagé — parcours du code

[← Toutes les fonctionnalités](../README.md) · [Fonctionnement](FUNCTIONALITY.md)

`FolderChannel.kt`, canal `ledger/folder` : `pick`, `list`, `read`, `write`, `delete`, `name`, `release`.

- `takePersistableUriPermission` garde l'accès ; `hasAccess` vérifie qu'il n'a pas été perdu.
- `write` cherche d'abord un fichier du même nom, sinon Android créerait « … (1).json ».
- Les accès disque vont sur **un** fil à part ; la réponse repart du fil principal.
- L'écriture n'est **pas atomique** : Android ne garantit pas le renommage. Un fichier à moitié écrit est sauté par les autres, puis relu.
