Ceci est le projet qui servira de CC2 pour le cours d'OUT 2025/2026.

## Promodex — Le Pokédex de la promo

Promodex transforme chaque élève de la promo en **carte à collectionner façon Pokédex**.

### Le concept

Chaque élève est une fiche avec :

- Une identité : nom, prénom, surnom
- Une filière (FISA / FAT / FISE) et une spécialité
- Un type humoristique : Café Addict, Noctambule, Fantôme.
- Des stats : PV, attaque, défense
- Un super pouvoir et une catchphrase

### Ce qu'on peut faire

- **Parcourir** toutes les cartes de la promo
- **Ajouter, modifier et supprimer** des fiches via un formulaire
- **Ouvrir un booster** : tirer aléatoirement 5 cartes et les révéler une par une

### Comment ça marche

Les cartes sont générées automatiquement à partir des données de chaque Student. La couleur de la carte dépend du type, les stats sont représentées visuellement, et chaque carte met en avant le surnom, le super pouvoir et la catchphrase.

Le backend expose une API REST, le frontend Angular consomme cette API et affiche les cartes.

Link to pull requests :

1. frontend PR:

- https://github.com/Skarginson/out-project-07/pull/1
- https://github.com/Skarginson/out-project-07/pull/5

3. backend PRs:

- https://github.com/Skarginson/out-project-07/pull/2
- https://github.com/Skarginson/out-project-07/pull/3
