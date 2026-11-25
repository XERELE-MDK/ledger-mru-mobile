# Shared code — setup

[← All features](../README.md) · [How it works](FUNCTIONALITY.md) · [Code walkthrough](CODE_WALKTHROUGH.md)

```bash
git clone --recursive https://github.com/XERELE-MDK/ledger-mru-mobile.git
```

Moving to a new release of the code:

```bash
git -C app fetch --tags
git -C app checkout v1.2.0
git add app
git commit -m "chore: ledger-mru-flutter v1.2.0"
```

On Windows: turn on developer mode, then `git config --global core.symlinks true` before cloning.
