package com.querypilot.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.querypilot.exception.InvalidGeneratedSqlException;

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

    /**
     * vérifie que le SQL généré est une requête de lecture autorisée.
     */
    public void validate(String sql) {

        if (sql == null || sql.isBlank()) {
            throw new InvalidGeneratedSqlException(
                    "La requête SQL générée est vide."
            );
        }

        String normalized = sql
                .trim()
                .toUpperCase(Locale.ROOT);

        if (!normalized.startsWith("SELECT")) {
            throw new InvalidGeneratedSqlException(
                    "Seules les requêtes SELECT sont autorisées."
            );
        }

        for (String keyword : FORBIDDEN_KEYWORDS) {

            if (normalized.matches(
                    "(?s).*\\b" + keyword + "\\b.*"
            )) {
                throw new InvalidGeneratedSqlException(
                        "Opération SQL interdite : " + keyword
                );
            }
        }

        // refuse plusieurs instructions SQL dans une seule réponse.
        String withoutFinalSemicolon =
                normalized.replaceFirst(";\\s*$", "");

        if (withoutFinalSemicolon.contains(";")) {
            throw new InvalidGeneratedSqlException(
                    "Une seule requête SQL est autorisée."
            );
        }
    }
}