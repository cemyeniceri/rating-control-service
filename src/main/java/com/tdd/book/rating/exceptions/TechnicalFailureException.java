package com.tdd.book.rating.exceptions;

public class TechnicalFailureException extends RuntimeException {
    public TechnicalFailureException(String message) {
        super(message);
    }
}
