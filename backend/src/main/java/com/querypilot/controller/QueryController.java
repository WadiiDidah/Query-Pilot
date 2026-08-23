package com.querypilot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querypilot.dto.QueryRequest;
import com.querypilot.dto.QueryResponse;
import com.querypilot.service.NaturalLanguageQueryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/queries")
public class QueryController {

    private final NaturalLanguageQueryService service;

    public QueryController(NaturalLanguageQueryService service) {
        this.service = service;
    }

    @PostMapping
    public QueryResponse query(@Valid @RequestBody QueryRequest request) {
        return service.process(request.question());
    }
}
