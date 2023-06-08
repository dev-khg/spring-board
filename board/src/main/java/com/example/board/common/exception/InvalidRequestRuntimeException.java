package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestRuntimeException extends BaseRuntimeException {
    public InvalidRequestRuntimeException(HttpStatus status, String message) {
        super(status, message);
    }
}
