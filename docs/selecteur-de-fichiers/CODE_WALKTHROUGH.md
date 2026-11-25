# Sélecteur de fichiers — parcours du code

[← Toutes les fonctionnalités](../README.md) · [Fonctionnement](FUNCTIONALITY.md)

`MainActivity.kt`, canal `ledger/files` :

| Méthode | Réponse | Dialogue |
|---|---|---|
| `save(name, content)` | `true`, ou `false` si annulé | `ACTION_CREATE_DOCUMENT` |
| `open()` | texte, ou `null` si annulé | `ACTION_OPEN_DOCUMENT` |

- Un deuxième appel pendant qu'un dialogue est ouvert est refusé (`busy`).
- `startActivityForResult` : `FlutterActivity` n'est pas une `ComponentActivity`, la nouvelle API n'y existe pas.
