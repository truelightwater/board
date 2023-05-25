package com.example.board.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handlerCustomException(final CustomException e) {
        log.error("handlerBusinessLogicException : {}", e.getMessage());

        return ResponseEntity
                .status(e.getExceptionCode().getStatus().value())
                .body(new ErrorResponse(e.getExceptionCode()));
    }

    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException : {}", e.getMessage());

        return ResponseEntity
                .status(ExceptionCode.METHOD_NOT_ALLOWED.getStatus().value())
                .body(new ErrorResponse(ExceptionCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
        log.error("handleException: {}", e.getMessage());

        return ResponseEntity
                .status(ExceptionCode.INTERNAL_SERVER_ERROR.getStatus().value())
                .body(new ErrorResponse(ExceptionCode.INTERNAL_SERVER_ERROR));
    }
}
