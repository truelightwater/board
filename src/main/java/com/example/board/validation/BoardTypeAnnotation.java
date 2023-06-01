package com.example.board.validation;

import com.example.board.model.BoardTypes;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BoardValidation.class)
public @interface BoardTypeAnnotation {
    BoardTypes boardTypeCheck();
}
