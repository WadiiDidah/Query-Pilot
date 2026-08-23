# QueryPilot

QueryPilot est une application full-stack permettant d'interroger une base de données PostgreSQL en langage naturel.

L'utilisateur pose une question en français, par exemple :

> Quels sont les clients qui habitent à Paris ?

QueryPilot utilise un LLM pour générer une requête SQL, vérifie sa sécurité, l'exécute sur PostgreSQL puis affiche les résultats dans une interface Angular.

## Fonctionnalités

- Questions en langage naturel
- Génération automatique de SQL avec un LLM
- Lecture dynamique du schéma PostgreSQL
- Validation des requêtes SQL avant exécution
- Autorisation uniquement des requêtes `SELECT`
- Blocage des opérations dangereuses (`DELETE`, `UPDATE`, `DROP`, etc.)
- Exécution sécurisée avec Spring JDBC
- Affichage dynamique des résultats
- Gestion des erreurs côté backend et frontend
- Historique persistant des requêtes
- Statuts `SUCCESS`, `BLOCKED` et `ERROR`
- Temps d'exécution et nombre de lignes retournées
- Interface responsive desktop / mobile

## Architecture

```text
Angular
   |
   | HTTP / REST
   v
Spring Boot
   |
   +--> NaturalLanguageQueryService
   |       |
   |       +--> DatabaseSchemaService
   |       +--> LlmService
   |       +--> SqlValidationService
   |       +--> QueryExecutionService
   |       +--> QueryHistoryService
   |
   v
PostgreSQL
```

## Stack technique

### Frontend

- Angular 22
- TypeScript
- HTML
- CSS
- Font Awesome

### Backend

- Java 21
- Spring Boot
- Spring Web
- Spring JDBC
- Spring Data JPA
- Bean Validation
- Maven

### Infrastructure

- Docker
- Docker Compose

### Base de données

- PostgreSQL

### Intelligence artificielle

- Groq API
- Modèle LLM configurable

## Sécurité SQL

QueryPilot fonctionne en lecture seule.

Avant l'exécution, chaque requête SQL générée est contrôlée par le backend.

Les opérations suivantes sont notamment interdites :

```text
INSERT
UPDATE
DELETE
DROP
ALTER
TRUNCATE
CREATE
GRANT
REVOKE
MERGE
CALL
COPY
```

Une requête valide doit commencer par `SELECT`.

Les requêtes contenant plusieurs instructions SQL sont également refusées.

## Historique

Chaque requête peut être enregistrée avec :

- la question utilisateur
- le SQL généré
- le statut
- le nombre de lignes retournées
- le temps d'exécution
- la date de création

Statuts disponibles :

```text
SUCCESS
BLOCKED
ERROR
```

## Exemple

Question :

```text
Quels sont les clients les plus récents ?
```

SQL généré :

```sql
SELECT id, name, city, created_at
FROM customers
ORDER BY created_at DESC
LIMIT 100;
```

QueryPilot valide ensuite le SQL, l'exécute sur PostgreSQL et retourne les résultats à l'interface Angular.

## Configuration

Les principales variables peuvent être configurées avec des variables d'environnement.

```bash
DB_URL=jdbc:postgresql://localhost:5432/querypilot
DB_USERNAME=querypilot
DB_PASSWORD=querypilot
GROQ_API_KEY=your_api_key
```

## Base de données avec Docker

PostgreSQL est lancé dans un conteneur Docker afin de simplifier la configuration de l'environnement de développement.

Démarrer les services :

```bash
docker compose up -d
```

Vérifier les conteneurs actifs :

```bash
docker compose ps
```

Arrêter les services :

```bash
docker compose down
```

Le backend Spring Boot se connecte ensuite à PostgreSQL grâce aux variables de configuration définies dans l'environnement.



## Lancer le backend

Depuis le dossier backend :

```bash
./mvnw spring-boot:run
```

L'API est disponible par défaut sur :

```text
http://localhost:8080
```

## Lancer le frontend

Depuis le dossier frontend :

```bash
npm install
npm start
```

L'application Angular est disponible sur :

```text
http://localhost:4200
```

## API

### Poser une question

```text
POST /api/query
```

Exemple de requête :

```json
{
  "question": "Quels sont les clients qui habitent à Paris ?"
}
```

### Consulter l'historique

```text
GET /api/history
```

## Structure du projet

```text
querypilot/
│
├── backend/
│   ├── src/main/java/com/querypilot/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   │
│   └── src/main/resources/
│
├── frontend/
│   └── src/app/
│       ├── models/
│       ├── pages/
│       └── services/
│
└── README.md
```

## Améliorations futures

Plusieurs fonctionnalités peuvent être ajoutées :

- authentification et gestion des utilisateurs
- connexion à plusieurs bases de données
- gestion des datasets
- dashboard et statistiques
- pagination de l'historique
- recherche et filtres
- export CSV / Excel
- gestion des rôles et permissions
- validation SQL plus avancée
- meilleure détection des intentions dangereuses
- support de plusieurs fournisseurs LLM
- tests unitaires et tests d'intégration
