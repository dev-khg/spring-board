package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizationException extends BaseException{
    public UnAuthorizationException(HttpStatus status, String message) {
        super(status, message);
    }
}
