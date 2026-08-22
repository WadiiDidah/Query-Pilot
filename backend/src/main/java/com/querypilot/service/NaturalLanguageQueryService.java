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

    public NaturalLanguageQueryService(
            LlmService llmService,
            DatabaseSchemaService schemaService,
            SqlValidationService sqlValidationService,
            QueryExecutionService queryExecutionService
    ) {
        this.llmService = llmService;
        this.schemaService = schemaService;
        this.sqlValidationService = sqlValidationService;
        this.queryExecutionService = queryExecutionService;
    }

    public QueryResponse process(String question) {

        String schema = schemaService.getSchema();

        // LLM transforme la question en SQL
        String sql = llmService.generateSql(
                question,
                schema
        );

        // on vérifie le SQL AVANT PostgreSQL
        sqlValidationService.validate(sql);

        //après validation, on peut exécuter
        List<Map<String, Object>> rows =
                queryExecutionService.execute(sql);

        //Réponse envoyée à Angular
        return new QueryResponse(
                question.trim(),
                sql,
                rows
        );
    }
}