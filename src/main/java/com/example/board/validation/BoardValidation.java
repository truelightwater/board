package com.example.board.validation;

import com.example.board.model.BoardRequest;
import com.example.board.model.BoardTypes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BoardValidation implements ConstraintValidator<BoardTypeAnnotation, BoardTypes> {

    // 복합필드에 대한 처리
    // 지금은 단일 필드에 대한 처리
    @Override
    public boolean isValid(BoardTypes value, ConstraintValidatorContext context) {
        if (value == BoardTypes.QUESTION) {
            BoardRequest.builder()
                    .dueDate(LocalDate.now())
                    .build();
            return true;
        }

        // false 일 때 Exception Handlering
        // 에러응답에 대한 스펙이 필요하다.
        // 응답이 어떻게 되는지 필요하다.
        // Exception
        // 필드에 대한 명세가 있어야 한다.
        return false;

    }
}
