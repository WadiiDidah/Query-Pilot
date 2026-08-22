package com.querypilot.controller;

import com.querypilot.dto.QueryRequest;
import com.querypilot.dto.QueryResponse;
import com.querypilot.service.NaturalLanguageQueryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queries")
@CrossOrigin(origins = "http://localhost:4200")
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
