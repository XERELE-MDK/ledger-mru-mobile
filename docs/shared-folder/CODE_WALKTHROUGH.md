# Shared folder — code walkthrough

[← All features](../README.md) · [How it works](FUNCTIONALITY.md)

`FolderChannel.kt`, channel `ledger/folder`: `pick`, `list`, `read`, `write`, `delete`, `name`, `release`.

- `takePersistableUriPermission` keeps the access; `hasAccess` checks it was not lost.
- `write` first looks for a file with the same name, otherwise Android would create "… (1).json".
- Disk access runs on **one** background thread; the answer goes back on the main thread.
- Writing is **not atomic**: Android does not guarantee rename. A half-written file is skipped by the others, then read again.
