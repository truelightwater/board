package com.example.board.exception2.resposestatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "errors.unauthorized")
public class UnauthorizedException extends RuntimeException {
}
