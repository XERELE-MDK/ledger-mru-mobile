# File picker — code walkthrough

[← All features](../README.md) · [How it works](FUNCTIONALITY.md)

`MainActivity.kt`, channel `ledger/files`:

| Method | Answer | Dialog |
|---|---|---|
| `save(name, content)` | `true`, or `false` if cancelled | `ACTION_CREATE_DOCUMENT` |
| `open()` | the text, or `null` if cancelled | `ACTION_OPEN_DOCUMENT` |

- A second call while a dialog is open is refused (`busy`).
- `startActivityForResult`: `FlutterActivity` is not a `ComponentActivity`, so the newer API does not exist there.
