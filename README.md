# Promodex — Le Pokédex de la promo

> Projet CC2 — Cours OUT 2025/2026 · EPISEN

Promodex transforme chaque élève de la promo en **carte à collectionner façon Pokédex**.
Un backend Spring Boot expose une API REST, un frontend Angular affiche les cartes avec des
couleurs et stats générées automatiquement.

---

## Concept

Chaque élève est une fiche avec :

| Champ     | Détail                                              |
|-----------|-----------------------------------------------------|
| Identité  | Nom, prénom, surnom                                 |
| Filière   | FISA / FAT / FISE + spécialité                      |
| Type      | Café Addict · Noctambule · Fantôme · Grind Master   |
| Stats     | PV, Attaque, Défense                                |
| Signature | Super pouvoir + catchphrase                         |

## Fonctionnalités

- **Galerie** — parcourir toutes les cartes de la promo
- **CRUD** — ajouter, modifier et supprimer des fiches via formulaire
- **Booster** — tirer aléatoirement 5 cartes et les révéler une par une

---

## Stack technique

| Couche   | Technologies                                              |
|----------|-----------------------------------------------------------|
| Backend  | Java 21 · Spring Boot 4 · JPA/Hibernate · H2 · MapStruct |
| Frontend | Angular 21 · TypeScript 5 · Tailwind CSS 4               |
| Tests    | JUnit · Mockito · JaCoCo (≥ 70%) · Vitest · Playwright   |
| CI/CD    | GitHub Actions                                            |

---

## Prérequis

- Java 21+
- Maven 3.8+
- Node.js LTS + npm 11+

---

## Lancer le projet

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

API disponible sur `http://localhost:8080`
Console H2 : `http://localhost:8080/h2-console` (JDBC URL : `jdbc:h2:mem:outprojectdb`)

### Frontend

```bash
cd frontend
npm install
npm run dev
```

App disponible sur `http://localhost:4200`

---

## Tests

### Backend

```bash
cd backend
./mvnw verify          # tests unitaires + intégration + couverture JaCoCo
```

Rapport de couverture : `backend/target/site/jacoco/index.html`

### Frontend

```bash
cd frontend
npm run test
```

---

## Structure du projet

```
out-project-07/
├── backend/          # API REST Spring Boot
│   └── src/
│       ├── main/java/com/example/backend/
│       │   ├── controller/   # Endpoints REST
│       │   ├── service/      # Logique métier
│       │   ├── repository/   # Accès données (JPA)
│       │   ├── dataHandler/  # Entités, DTOs, Mappers
│       │   └── exception/    # Gestion d'erreurs globale
│       └── test/             # Tests unitaires & intégration
├── frontend/         # Application Angular
│   └── src/app/
│       ├── core/     # Services & modèles
│       ├── pages/    # Pages (liste, détail, création, édition)
│       ├── features/ # Galerie & booster
│       └── shared/   # Composants réutilisables (carte, formulaire)
└── .github/workflows/ci.yml  # Pipeline CI
```

---

## Pull Requests

### Frontend
- [PR #1](https://github.com/Skarginson/out-project-07/pull/1)
- [PR #5](https://github.com/Skarginson/out-project-07/pull/5)

### Backend
- [PR #2](https://github.com/Skarginson/out-project-07/pull/2)
- [PR #3](https://github.com/Skarginson/out-project-07/pull/3)
