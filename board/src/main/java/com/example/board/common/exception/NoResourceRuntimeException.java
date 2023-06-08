package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public class NoResourceRuntimeException extends BaseRuntimeException {
    public NoResourceRuntimeException(HttpStatus status, String message) {
        super(status, message);
    }
}
