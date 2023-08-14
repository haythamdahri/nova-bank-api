package org.newyork.microservices.novabank.exceptions;

public class NoAccountFoundException extends RuntimeException {

    public NoAccountFoundException() {
    }

    public NoAccountFoundException(String message) {
        super(message);
    }

    public NoAccountFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAccountFoundException(Throwable cause) {
        super(cause);
    }

    public NoAccountFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
