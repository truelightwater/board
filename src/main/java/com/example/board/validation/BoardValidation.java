package com.example.board.validation;

import com.example.board.model.BoardRequest;
import com.example.board.model.BoardTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerErrorException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;


@Slf4j
public class BoardValidation implements ConstraintValidator<BoardTypeAnnotation, BoardRequest> {
    private String message;
    private String boardType;
    private String dueDate;

    @Override
    public void initialize(BoardTypeAnnotation constraintAnnotation) {
        message = constraintAnnotation.message();
        boardType = constraintAnnotation.boardType();
        dueDate = constraintAnnotation.dueDate();
    }


    // 복합필드에 대한 처리
    @Override
    public boolean isValid(BoardRequest boardRequest, ConstraintValidatorContext context) {
        // false 일 때 Exception Handlering
        // 에러응답에 대한 스펙이 필요하다.
        // 응답이 어떻게 되는지 필요하다.
        // Exception
        // 필드에 대한 명세가 있어야 한다.

        LocalDate dueDateCheck = getFieldValueV1(boardRequest, dueDate);
        BoardTypes boardTypeCheck = getFieldValueV2(boardRequest, boardType);

        if (!(boardTypeCheck == BoardTypes.QUESTION)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message);
        } else {
            LocalDate localDate = LocalDate.now().plusDays(7);
            setFieldValue(boardRequest, dueDate, localDate);
        }

        return true;
    }


    private LocalDate getFieldValueV1(Object object, String filedName) {
        Class<?> clazz = object.getClass();
        Field dataField;

        try {
            dataField = clazz.getDeclaredField(filedName);
            dataField.setAccessible(true);

            Object target = dataField.get(object);
            if (!(target instanceof LocalDate)) {
                throw new ClassCastException("casting exception");
            }
            return (LocalDate) target;
        } catch (NoSuchFieldException e) {
            log.error("NoSuchFieldException", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        }
        throw new ServerErrorException("Not Found Field");
    }

    private BoardTypes getFieldValueV2(Object object, String filedName) {
        Class<?> clazz = object.getClass();
        Field dataField;

        try {
            dataField = clazz.getDeclaredField(filedName);
            dataField.setAccessible(true);

            Object target = dataField.get(object);
            if (!(target instanceof BoardTypes)) {
                throw new ClassCastException("casting exception");
            }
            return (BoardTypes) target;
        } catch (NoSuchFieldException e) {
            log.error("NoSuchFieldException", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        }
        throw new ServerErrorException("Not Found Field");
    }

    private void setFieldValue(Object object, String fieldName, Object value) {
        Class<?> clazz = object.getClass();
        Field field;

        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException e) {
            log.error("NoSuchFieldException", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException", e);
        }
    }

}
