package com.querypilot.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Gère les erreurs de validation des données reçues par l'API.
     * Exemple : question vide.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(
            MethodArgumentNotValidException exception
    ) {

        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Requête invalide");

        return Map.of(
                "message", message
        );
    }

    
    @ExceptionHandler(InvalidGeneratedSqlException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Map<String, Object> handleInvalidSql(
            InvalidGeneratedSqlException exception
    ) {

        return Map.of(
                "status", 422,
                "error", "SQL_BLOCKED",
                "message", exception.getMessage()
        );
    }

    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleUnexpectedError(
            Exception exception
    ) {

        return Map.of(
                "status", 500,
                "error", "INTERNAL_ERROR",
                "message", "Une erreur interne est survenue."
        );
    }
}