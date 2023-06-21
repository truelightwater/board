package com.example.board.exception.errorstatus;

import org.springframework.validation.BindingResult;


public class PasswordSizeInvalidException extends RuntimeException {

    BindingResult bindingResult;

    public PasswordSizeInvalidException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
