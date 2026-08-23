package com.querypilot.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.querypilot.dto.QueryResponse;
import com.querypilot.exception.InvalidGeneratedSqlException;

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

    long startTime = System.currentTimeMillis();

    String sql = null;

    try {

        String schema = schemaService.getSchema();

        // Génération SQL par le LLM
        sql = llmService.generateSql(
                question,
                schema
        );

        // Validation sécurité
        sqlValidationService.validate(sql);

        // Exécution PostgreSQL
        List<Map<String, Object>> rows =
                queryExecutionService.execute(sql);

        long executionTime =
                System.currentTimeMillis() - startTime;

        // Historique SUCCESS
        queryHistoryService.saveSuccess(
                question.trim(),
                sql,
                rows.size(),
                executionTime
        );

        return new QueryResponse(
                question.trim(),
                sql,
                rows
        );

    } catch (InvalidGeneratedSqlException exception) {

        long executionTime =
                System.currentTimeMillis() - startTime;

        queryHistoryService.saveBlocked(
                question.trim(),
                sql != null ? sql : "",
                executionTime
        );

        throw exception;

    } catch (Exception exception) {

        long executionTime =
                System.currentTimeMillis() - startTime;

        queryHistoryService.saveError(
                question.trim(),
                sql != null ? sql : "",
                executionTime
        );

        throw exception;
    }
}
}