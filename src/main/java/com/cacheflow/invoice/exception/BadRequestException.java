package com.cacheflow.invoice.exception;

public class BadRequestException extends Exception {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
