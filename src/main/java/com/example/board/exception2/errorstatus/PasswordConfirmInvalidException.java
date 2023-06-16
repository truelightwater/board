package com.example.board.exception2.errorstatus;

import org.springframework.validation.BindingResult;

public class PasswordConfirmInvalidException extends RuntimeException {

    BindingResult bindingResult;

    public PasswordConfirmInvalidException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
