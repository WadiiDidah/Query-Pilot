# QueryPilot

Application Angular + Spring Boot qui transforme des questions en langage naturel en requêtes SQL contrôlées.

## Architecture

Angular → Spring Boot → LLM API → validation SQL → PostgreSQL

## État actuel

- Spring Boot initialisé
- PostgreSQL via Docker Compose
- endpoint `POST /api/queries`
- validation des requêtes entrantes
- exécution SQL de test avec `JdbcTemplate`
- structure prête pour le futur `LlmService`
- frontend Angular réservé dans `frontend/`

## Démarrage

### PostgreSQL

```bash
cd infra
docker compose up -d
```

### Backend

```bash
cd backend
mvn spring-boot:run
```

### Test

```bash
curl -X POST http://localhost:8080/api/queries   -H "Content-Type: application/json"   -d '{"question":"Quelle base de données utilises-tu ?"}'
```

## Sécurité prévue

Le SQL généré par le LLM sera validé avant exécution : uniquement `SELECT`, blocage des requêtes destructrices, limite des résultats et utilisateur PostgreSQL en lecture seule.
