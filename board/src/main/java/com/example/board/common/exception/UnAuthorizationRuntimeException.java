package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizationRuntimeException extends BaseRuntimeException {
    public UnAuthorizationRuntimeException(HttpStatus status, String message) {
        super(status, message);
    }
}
