# Confidentialité — fonctionnement

[← Toutes les fonctionnalités](../README.md) · [Parcours du code](CODE_WALKTHROUGH.md)

- **Aucune permission Internet** : l'app ne peut pas se connecter. La CI le vérifie à chaque envoi.
- Aucun compte, aucune publicité, aucune statistique.
- Les données sont dans l'espace privé de l'app, en clair, comme le fichier d'export et le dossier partagé.

⚠️ Android peut inclure ces données dans la **sauvegarde Google** du téléphone si elle est activée : l'app ne l'interdit pas.
