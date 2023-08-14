package org.newyork.microservices.novabank.exceptions;

public class NoBankUserFoundException extends RuntimeException {

    public NoBankUserFoundException() {
    }

    public NoBankUserFoundException(String message) {
        super(message);
    }

    public NoBankUserFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoBankUserFoundException(Throwable cause) {
        super(cause);
    }

    public NoBankUserFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
