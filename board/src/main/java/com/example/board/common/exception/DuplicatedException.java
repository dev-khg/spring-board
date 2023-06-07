package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public class DuplicatedException extends BaseException{

    public DuplicatedException(HttpStatus status, String message) {
        super(status, message);
    }
}
