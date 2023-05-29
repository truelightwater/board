package com.example.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private ExceptionCode exceptionCode;


    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fileName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fileName, errorMessage);
        });

        return errors;
    }

}
