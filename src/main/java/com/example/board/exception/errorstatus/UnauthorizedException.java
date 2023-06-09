package com.example.board.exception.errorstatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "errors.unauthorized")
public class UnauthorizedException extends RuntimeException {
}
