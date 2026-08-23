package com.querypilot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.querypilot.model.QueryHistory;

public interface QueryHistoryRepository
        extends JpaRepository<QueryHistory, Long> {

    List<QueryHistory> findAllByOrderByCreatedAtDesc();

}
