package com.example.board.exception2.resposestatus;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "errors.badRequest")
public class NotFoundException extends RuntimeException{
}
