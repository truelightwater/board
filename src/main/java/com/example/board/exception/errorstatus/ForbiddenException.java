package com.example.board.exception.errorstatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "errors.Forbidden")
public class ForbiddenException extends RuntimeException {
}
