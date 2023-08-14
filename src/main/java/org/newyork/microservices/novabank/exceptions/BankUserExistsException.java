package org.newyork.microservices.novabank.exceptions;

public class BankUserExistsException extends RuntimeException {

    public BankUserExistsException() {
    }

    public BankUserExistsException(String message) {
        super(message);
    }

    public BankUserExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankUserExistsException(Throwable cause) {
        super(cause);
    }

    public BankUserExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
