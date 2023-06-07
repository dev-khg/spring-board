package com.example.board.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends BaseException{
    public InvalidRequestException(HttpStatus status, String message) {
        super(status, message);
    }
}
