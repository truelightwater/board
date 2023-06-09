package com.example.board.validation;

import com.example.board.model.BoardRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerErrorException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

@Slf4j
public class PassWordConfirmCheckValidator implements ConstraintValidator<PassWordConfirmCheck, BoardRequest> {

    private String message;
    private String boardPass;
    private String boardPassConfirm;

    @Override
    public void initialize(PassWordConfirmCheck constraintAnnotation) {
        message = constraintAnnotation.message();
        boardPass = constraintAnnotation.boardPass();
        boardPassConfirm = constraintAnnotation.boardPassConfirm();
    }

    @Override
    public boolean isValid(BoardRequest boardRequest, ConstraintValidatorContext context) {

        boolean flag = true;

        String password = getFieldValue(boardRequest, boardPass);
        String confirm = getFieldValue(boardRequest, boardPassConfirm);

        if (!password.equals(confirm)) {
            context.disableDefaultConstraintViolation();;
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(password)
                    .addConstraintViolation();
            flag = false;
        }

        return flag;
    }

    private String getFieldValue(Object o, String fieldName) {
        Class<?> clazz = o.getClass();
        Field dataField;

        try {
            dataField = clazz.getDeclaredField(fieldName);
            dataField.setAccessible(true);
            Object target = dataField.get(o);

            if (!(target instanceof String)) {
                throw new ClassCastException("casting exception");
            }
            return (String) target;

        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        throw new ServerErrorException("Not Found Field");
    }
}
