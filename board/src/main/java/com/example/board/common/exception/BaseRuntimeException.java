package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseRuntimeException extends RuntimeException{
    private HttpStatus httpStatus;

    public BaseRuntimeException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }

    public HttpStatus getStatus() { return this.httpStatus; }
}
