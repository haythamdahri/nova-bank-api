package org.newyork.microservices.novabank.exceptions;

public class NoSavingAccountFoundException extends NoAccountFoundException {

    public NoSavingAccountFoundException() {
    }

    public NoSavingAccountFoundException(String message) {
        super(message);
    }

    public NoSavingAccountFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSavingAccountFoundException(Throwable cause) {
        super(cause);
    }

    public NoSavingAccountFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
