package com.example.board.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected Object handlerMethodArgumentNotValidException
            (MethodArgumentNotValidException e, HttpServletRequest request) {
        return e.getBindingResult().getAllErrors();
    }
}
