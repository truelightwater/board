package com.example.board.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final int code;
    private final String message;

    public ErrorResponse(ExceptionCode exceptionCode) {
        this.status = exceptionCode.getStatus().value();
        this.error = exceptionCode.getStatus().name();
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
