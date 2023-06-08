package com.example.board.server.advice;

import com.example.board.common.exception.BaseRuntimeException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseEntity<ErrorReturn> myRuntimeException(BaseRuntimeException e) {
        log.error("\n[BaseRuntimeException]", e);

        return ResponseEntity.status(e.getStatus().value()).body(new ErrorReturn(e));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorReturn> runtimeException(RuntimeException e) {
        log.error("\n[RuntimeException]", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorReturn(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }


    @Getter
    static class ErrorReturn {
        private String reason;
        private String message;

//        protected ErrorReturn(HttpStatus httpStatus, String message) {
//            this.httpStatus = httpStatus;
//            this.message = message;
//        }

        ErrorReturn(BaseRuntimeException e) {
            this.reason = e.getStatus().getReasonPhrase();
            this.message = e.getMessage();
        }

        ErrorReturn(HttpStatus status, String message) {
            this.reason = status.getReasonPhrase();
            this.message = message;
        }
    }
}
