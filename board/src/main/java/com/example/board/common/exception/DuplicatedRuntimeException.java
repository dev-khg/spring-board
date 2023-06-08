package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedRuntimeException extends BaseRuntimeException {

    public DuplicatedRuntimeException(HttpStatus status, String message) {
        super(status, message);
    }
}
