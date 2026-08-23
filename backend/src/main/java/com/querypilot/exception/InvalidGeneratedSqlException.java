package com.querypilot.exception;

public class InvalidGeneratedSqlException
        extends RuntimeException {

    public InvalidGeneratedSqlException(String message) {
        super(message);
    }
}