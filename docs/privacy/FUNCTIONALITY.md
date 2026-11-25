# Privacy — how it works

[← All features](../README.md) · [Code walkthrough](CODE_WALKTHROUGH.md)

- **No Internet permission**: the app cannot connect anywhere. CI checks it on every push.
- No account, no ads, no analytics.
- Data lives in the app's private storage, in plain text, like the export file and the shared folder.

⚠️ Android may include this data in the phone's **Google backup** if it is turned on: the app does not forbid it.
