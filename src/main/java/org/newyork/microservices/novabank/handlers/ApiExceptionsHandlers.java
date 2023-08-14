package org.newyork.microservices.novabank.handlers;

import org.newyork.microservices.novabank.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionsHandlers {

    @ExceptionHandler(value = {
            NoBankUserFoundException.class,
            NoAccountFoundException.class,
            NoSavingAccountFoundException.class
    })
    public ResponseEntity<ApiExceptionResponse> handleNotFoundException(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ApiExceptionResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .httpStatus(HttpStatus.NOT_FOUND)
                                .message(e.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(value = {
            BankUserExistsException.class,
            BusinessException.class
    })
    public ResponseEntity<ApiExceptionResponse> handleBadRequestExceptions(final RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiExceptionResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .message(e.getMessage())
                                .build()
                );
    }

}
