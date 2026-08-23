package com.querypilot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querypilot.model.QueryHistory;
import com.querypilot.service.QueryHistoryService;

@RestController
@RequestMapping("/api/history")
public class QueryHistoryController {

    private final QueryHistoryService queryHistoryService;

    public QueryHistoryController(
            QueryHistoryService queryHistoryService
    ) {
        this.queryHistoryService = queryHistoryService;
    }

    
    @GetMapping
    public List<QueryHistory> getHistory() {
        return queryHistoryService.findAll();
    }
}