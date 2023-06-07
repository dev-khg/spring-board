package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException{
    private HttpStatus httpStatus;

    public BaseException(HttpStatus status, String message) {
        super(message);
        this.httpStatus = status;
    }

    public HttpStatus getStatus() { return this.httpStatus; }
}
