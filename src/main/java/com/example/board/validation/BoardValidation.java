package com.example.board.validation;

import com.example.board.model.BoardRequest;
import com.example.board.model.BoardTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.server.ServerErrorException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.time.LocalDate;


@Slf4j
public class BoardValidation implements ConstraintValidator<BoardTypeAnnotation, Object> {
    private String boardType;
    private String dueDate;

    @Override
    public void initialize(BoardTypeAnnotation constraintAnnotation) {
        boardType = constraintAnnotation.boardType();
        dueDate = constraintAnnotation.dueDate();
    }

    // 복합필드에 대한 처리
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        // false 일 때 Exception Handlering
        // 에러응답에 대한 스펙이 필요하다.
        // 응답이 어떻게 되는지 필요하다.
        // Exception
        // 필드에 대한 명세가 있어야 한다.
        Object boardTypeCheck = new BeanWrapperImpl(object).getPropertyValue(boardType);
        // call by name, reference

        Object dueDateCheck = new BeanWrapperImpl(object).getPropertyValue(dueDate);
        // LocalDate dueDateCheck = getFieldValue(object, dueDate);

        if (boardTypeCheck.equals(BoardTypes.QUESTION)) {

            LocalDate currentDate = LocalDate.now();
            LocalDate fixDate = currentDate.plusDays(7);
            dueDateCheck = fixDate;

            log.info("게시판 타입이 질문입니다.");
            return true;
        }

        return true;
    }


   private LocalDate getFieldValue(Object object, String filedName) {
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

}
