package com.querypilot.service;

import org.springframework.stereotype.Service;

@Service
public class DatabaseSchemaService {

    public String getSchema() {

        return """
                customers(
                    id BIGINT,
                    name VARCHAR,
                    city VARCHAR,
                    created_at TIMESTAMP
                )

                orders(
                    id BIGINT,
                    customer_id BIGINT,
                    total_amount NUMERIC,
                    status VARCHAR,
                    created_at TIMESTAMP
                )

                Relationship:
                orders.customer_id -> customers.id
                """;
    }
}