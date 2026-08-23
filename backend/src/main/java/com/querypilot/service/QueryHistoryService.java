package com.querypilot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.querypilot.model.QueryHistory;
import com.querypilot.repository.QueryHistoryRepository;

@Service
public class QueryHistoryService {

    private final QueryHistoryRepository repository;

    public QueryHistoryService(
            QueryHistoryRepository repository
    ) {
        this.repository = repository;
    }

    public void saveSuccess(
            String question,
            String sql,
            int rowCount,
            long executionTimeMs
    ) {

        QueryHistory history = new QueryHistory(
                question,
                sql,
                "SUCCESS",
                rowCount,
                executionTimeMs
        );

        repository.save(history);
    }

    public List<QueryHistory> findAll() {
        return repository.findAllByOrderByCreatedAtDesc();
    }
}