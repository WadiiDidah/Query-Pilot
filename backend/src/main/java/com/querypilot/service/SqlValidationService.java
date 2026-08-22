package com.querypilot.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class SqlValidationService {

    private static final List<String> FORBIDDEN_KEYWORDS = List.of(
            "INSERT",
            "UPDATE",
            "DELETE",
            "DROP",
            "ALTER",
            "TRUNCATE",
            "CREATE",
            "GRANT",
            "REVOKE",
            "MERGE",
            "CALL",
            "COPY"
    );

    public void validate(String sql) {

        if (sql == null || sql.isBlank()) {
            throw new IllegalArgumentException("La requête SQL est vide.");
        }

        String normalized = sql
                .trim()
                .toUpperCase(Locale.ROOT);

        if (!normalized.startsWith("SELECT")) {
            throw new IllegalArgumentException(
                    "Seules les requêtes SELECT sont autorisées."
            );
        }

        for (String keyword : FORBIDDEN_KEYWORDS) {
            if (normalized.matches(
                    "(?s).*\\b" + keyword + "\\b.*"
            )) {
                throw new IllegalArgumentException(
                        "Opération SQL interdite : " + keyword
                );
            }
        }

        // On refuse plusieurs instructions SQL dans la même réponse.
        String withoutFinalSemicolon =
                normalized.replaceFirst(";\\s*$", "");

        if (withoutFinalSemicolon.contains(";")) {
            throw new IllegalArgumentException(
                    "Une seule requête SQL est autorisée."
            );
        }
    }
}