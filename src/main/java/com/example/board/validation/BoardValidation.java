package com.example.board.validation;

import com.example.board.model.BoardDTO;
import com.example.board.model.BoardRequest;
import com.example.board.model.BoardResponse;
import com.example.board.model.BoardTypes;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.server.ServerErrorException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.apache.tomcat.jni.Time.now;


@Slf4j
public class BoardValidation implements ConstraintValidator<BoardTypeAnnotation, BoardDTO> {
    private String message;
    private String boardType;
    private String dueDate;


    @Override
    public void initialize(BoardTypeAnnotation constraintAnnotation) {
        message = constraintAnnotation.message();
        boardType = constraintAnnotation.boardType();
        dueDate = constraintAnnotation.dueDate();
    }

    // false 일 때 Exception Handlering
    // 에러 응답에 대한 스펙이 필요하다.
    // 응답이 어떻게 되는지 필요하다.
    // Exception
    // 필드에 대한 명세가 있어야 한다.
    // 복합필드에 대한 처리
    @Override
    public boolean isValid(BoardDTO boardDTO, ConstraintValidatorContext context) {

        boolean flag = true;
        if (boardDTO.getBoardType() == BoardTypes.QUESTION) {
            boardDTO.setDueDate(LocalDateTime.now().plusDays(7).toLocalDate());
            return flag;
        }
        return flag;
    }


    /*
        // call by name, reference
        Object boardTypeCheck = new BeanWrapperImpl(value).getPropertyValue(boardType);
        Object dueDateCheck = new BeanWrapperImpl(value).getPropertyValue(dueDate);

        BoardTypes boardTypeCheck = getFieldValueV2(value, boardType);
        LocalDate dueDateCheck = getFieldValueV1(value, dueDate);

        if (boardTypeCheck == BoardTypes.QUESTION) {

            dueDateCheck.plusYears(now()).plusMonths(now()).plusWeeks(now()).plusDays(7);
            LocalDate currentDay = LocalDate.now();
            LocalDate dueDate = currentDay.plusDays(7);

            dueDate.atStartOfDay(ZoneId.from(dueDate));

            log.info("게시판 타입이 질문입니다.");
            return true;
        }*/
   /*private LocalDate getFieldValueV1(Object object, String filedName) {
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
*/
}
