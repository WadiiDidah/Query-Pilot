package com.querypilot.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.querypilot.dto.QueryResponse;

@Service
public class NaturalLanguageQueryService {

    private final LlmService llmService;
    private final DatabaseSchemaService schemaService;
    private final SqlValidationService sqlValidationService;
    private final QueryExecutionService queryExecutionService;
    private final QueryHistoryService queryHistoryService;

    public NaturalLanguageQueryService(
            LlmService llmService,
            DatabaseSchemaService schemaService,
            SqlValidationService sqlValidationService,
            QueryExecutionService queryExecutionService,
            QueryHistoryService queryHistoryService) {

        this.llmService = llmService;
        this.schemaService = schemaService;
        this.sqlValidationService = sqlValidationService;
        this.queryExecutionService = queryExecutionService;
        this.queryHistoryService = queryHistoryService;
    }

    public QueryResponse process(String question) {

        // début du chronométrage de la requête
        long startTime = System.currentTimeMillis();

        // récupération du schéma PostgreSQL envoyé au LLM
        String schema = schemaService.getSchema();

        // transformation de la question en requête SQL
        String sql = llmService.generateSql(
                question,
                schema
        );

        // vérification de la sécurité du SQL généré
        sqlValidationService.validate(sql);

        // exécution de la requête SQL validée
        List<Map<String, Object>> rows =
                queryExecutionService.execute(sql);

        // calcul du temps total de traitement
        long executionTime =
                System.currentTimeMillis() - startTime;

        // enregistrement de la requête réussie dans l'historique
        queryHistoryService.saveSuccess(
                question.trim(),
                sql,
                rows.size(),
                executionTime
        );

        // réponse renvoyée au frontend Angular
        return new QueryResponse(
                question.trim(),
                sql,
                rows
        );
    }
}