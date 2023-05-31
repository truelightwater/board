package com.example.board.validation;

import com.example.board.model.BoardRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
public class BoardValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BoardRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoardRequest request = (BoardRequest) target;


    }
}
