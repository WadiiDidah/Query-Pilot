package com.querypilot.dto;

import java.util.List;
import java.util.Map;

public record QueryResponse(
        String question,
        String sql,
        List<Map<String, Object>> rows
) {}
