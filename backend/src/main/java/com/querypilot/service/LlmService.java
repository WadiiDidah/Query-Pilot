package com.querypilot.service;

public interface LlmService {

    String generateSql(
            String question,
            String databaseSchema
    );
}