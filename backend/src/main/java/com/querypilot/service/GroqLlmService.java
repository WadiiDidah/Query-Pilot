package com.querypilot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GroqLlmService implements LlmService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${groq.model}")
    private String model;

    public GroqLlmService(
            @Value("${groq.api-key}") String apiKey,
            @Value("${groq.base-url}") String baseUrl,
            ObjectMapper objectMapper
    ) {

        this.objectMapper = objectMapper;

        this.restClient = RestClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(
                        HttpHeaders.AUTHORIZATION,
                        "Bearer " + apiKey
                )
                .defaultHeader(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE
                )
                .build();
    }

    @Override
    public String generateSql(
            String question,
            String databaseSchema
    ) {

        String systemPrompt = """
                You are a PostgreSQL SQL generator.

                Convert the user's natural-language question
                into exactly one PostgreSQL SELECT query.

                Rules:
                - Return SQL only.
                - Do not use markdown.
                - Do not explain the query.
                - Only SELECT statements are allowed.
                - Never generate INSERT.
                - Never generate UPDATE.
                - Never generate DELETE.
                - Never generate DROP.
                - Never generate ALTER.
                - Never generate TRUNCATE.
                - Use only tables and columns from the provided schema.
                - Add LIMIT 100 when appropriate.

                Database schema:

                %s
                """.formatted(databaseSchema);

        Map<String, Object> body = Map.of(
                "model", model,
                "temperature", 0,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", systemPrompt
                        ),
                        Map.of(
                                "role", "user",
                                "content", question
                        )
                )
        );

        String response = restClient
                .post()
                .uri("/chat/completions")
                .body(body)
                .retrieve()
                .body(String.class);

        try {

            JsonNode json =
                    objectMapper.readTree(response);

            return json
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText()
                    .trim();

        } catch (Exception exception) {

            throw new IllegalStateException(
                    "Impossible de lire la réponse du LLM",
                    exception
            );
        }
    }
}