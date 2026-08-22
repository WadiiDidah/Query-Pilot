package com.querypilot.service;

import com.querypilot.dto.QueryResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NaturalLanguageQueryService {

    private final JdbcTemplate jdbcTemplate;

    public NaturalLanguageQueryService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public QueryResponse process(String question) {
        String sql = """
                SELECT
                    current_database() AS database,
                    current_user AS db_user,
                    version() AS postgres_version
                """;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        return new QueryResponse(question.trim(), sql.trim(), rows);
    }
}
