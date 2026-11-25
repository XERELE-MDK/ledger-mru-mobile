# Code partagé — mise en place

[← Toutes les fonctionnalités](../README.md) · [Fonctionnement](FUNCTIONALITY.md) · [Parcours du code](CODE_WALKTHROUGH.md)

```bash
git clone --recursive https://github.com/XERELE-MDK/ledger-mru-mobile.git
```

Passer à une nouvelle version du code :

```bash
git -C app fetch --tags
git -C app checkout v1.2.0
git add app
git commit -m "chore: ledger-mru-flutter v1.2.0"
```

Sous Windows : mode développeur, puis `git config --global core.symlinks true` avant de cloner.
