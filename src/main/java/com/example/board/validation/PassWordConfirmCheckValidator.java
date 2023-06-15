package com.example.board.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PassWordConfirmCheckValidator implements ConstraintValidator<PassWordConfirmCheck, Object> {
    private String boardPass;
    private String boardPassConfirm;

    @Override
    public void initialize(PassWordConfirmCheck constraintAnnotation) {
        boardPass = constraintAnnotation.boardPass();
        boardPassConfirm = constraintAnnotation.boardPassConfirm();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(object).getPropertyValue(boardPass);
        Object fieldMatchValue = new BeanWrapperImpl(object).getPropertyValue(boardPassConfirm);

        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldValue == null;
        }

    }

}
