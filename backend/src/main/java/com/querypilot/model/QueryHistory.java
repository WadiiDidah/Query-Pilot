package com.querypilot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "query_history")
public class QueryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(name = "generated_sql", nullable = false, columnDefinition = "TEXT")
    private String generatedSql;

    @Column(nullable = false)
    private String status;

    @Column(name = "row_count")
    private Integer rowCount;

    @Column(name = "execution_time_ms")
    private Long executionTimeMs;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected QueryHistory() {
    }

    public QueryHistory(
            String question,
            String generatedSql,
            String status,
            Integer rowCount,
            Long executionTimeMs
    ) {
        this.question = question;
        this.generatedSql = generatedSql;
        this.status = status;
        this.rowCount = rowCount;
        this.executionTimeMs = executionTimeMs;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getGeneratedSql() {
        return generatedSql;
    }

    public String getStatus() {
        return status;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public Long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}