package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public class NoResourceException extends BaseException{
    public NoResourceException(HttpStatus status, String message) {
        super(status, message);
    }
}
