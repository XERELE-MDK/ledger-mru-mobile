# Documentation

One feature = one folder:
- `FUNCTIONALITY.md` — what it does, for the person using it;
- `CODE_WALKTHROUGH.md` — how it is built;
- `INSTALL.md` — when there is something to set up.

The app's features (entries, reports, themes, languages, sync…) are documented once, in [ledger-mru-flutter/docs](https://github.com/XERELE-MDK/ledger-mru-flutter/tree/main/docs). Here: what is specific to Android.

| Feature | In short | Go further |
|---|---|---|
| [Shared code](shared-code/FUNCTIONALITY.md) | The Dart code comes from a submodule | [code](shared-code/CODE_WALKTHROUGH.md) · [setup](shared-code/INSTALL.md) |
| [File picker](file-picker/FUNCTIONALITY.md) | Import and export through the system | [code](file-picker/CODE_WALKTHROUGH.md) |
| [Shared folder](shared-folder/FUNCTIONALITY.md) | Lasting access to a single folder | [code](shared-folder/CODE_WALKTHROUGH.md) |
| [Icon](icon/FUNCTIONALITY.md) | Adaptive, and themed on Android 13+ | [code](icon/CODE_WALKTHROUGH.md) |
| [Privacy](privacy/FUNCTIONALITY.md) | No account, no network | [code](privacy/CODE_WALKTHROUGH.md) |
| [APKs and signing](release/FUNCTIONALITY.md) | Four APKs per release | [code](release/CODE_WALKTHROUGH.md) · [setup](release/INSTALL.md) |

See also: [Versioning](VERSIONING.md) · [Changelog](../CHANGELOG.md)
