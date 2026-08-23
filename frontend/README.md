# QueryPilot Frontend

Interface web de **QueryPilot**, développée avec Angular.

Le frontend permet à l'utilisateur de poser une question en langage naturel, d'envoyer cette question à l'API Spring Boot et d'afficher la requête SQL générée ainsi que les résultats retournés par PostgreSQL.

## Technologies

- Angular 22
- TypeScript
- HTML
- CSS
- Angular Standalone Components
- Angular Router
- Angular HttpClient
- Font Awesome

## Fonctionnalités

- Saisie d'une question en langage naturel
- Envoi de la question vers l'API QueryPilot
- Affichage du SQL généré
- Affichage dynamique des résultats sous forme de tableau
- Gestion des états de chargement
- Gestion et affichage des erreurs
- Suggestions de questions
- Copie du SQL généré
- Historique des requêtes
- Affichage des statuts `SUCCESS`, `BLOCKED` et `ERROR`
- Interface responsive
- Sidebar de navigation
- Menu mobile

## Structure

```text
src/app/
│
├── models/
│   └── query.model.ts
│
├── pages/
│   ├── query-page/
│   │   ├── query-page.component.ts
│   │   ├── query-page.component.html
│   │   └── query-page.component.css
│   │
│   └── history/
│       ├── history.component.ts
│       ├── history.component.html
│       └── history.component.css
│
├── services/
│   └── query.service.ts
│
├── app.routes.ts
└── app.component.ts
```

## Installation

Installer les dépendances :

```bash
npm install
```

## Lancement

Démarrer le serveur Angular :

```bash
npm start
```

ou :

```bash
ng serve
```

L'application est ensuite disponible sur :

```text
http://localhost:4200
```

## Backend

Le frontend communique avec l'API Spring Boot de QueryPilot.

Par défaut :

```text
http://localhost:8080
```

Principaux endpoints utilisés :

```text
POST /api/query
GET  /api/history
```

## Exemple

L'utilisateur peut saisir :

```text
Quels sont les clients qui habitent à Paris ?
```

Le frontend envoie la question au backend puis affiche le SQL généré :

```sql
SELECT id, name, city
FROM customers
WHERE city = 'Paris'
LIMIT 100;
```

Les données retournées sont ensuite affichées automatiquement sous forme de tableau.

## Interface

QueryPilot propose une interface responsive adaptée aux ordinateurs, tablettes et mobiles.


## Améliorations futures

- Favoris
- Gestion des datasets
- Dashboard
- Authentification
- Gestion des utilisateurs
- Rôles et permissions
- Recherche dans l'historique
- Pagination
- Export des résultats en CSV ou Excel