package com.example.board.validation;

import com.example.board.model.BoardTypes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BoardValidation.class)
public @interface BoardTypeAnnotation {

    // invalid 할 때 표시할 기본 에러 메세지
    String message() default "게시판 타입이 질문이면 마감일이 설정됩니다.";
    String dueDate();
    String boardType();


    /**
     * Spring 표준을 준수하기 위한 상용구 코드 ????
     * @return
     */
    // 유효성 검사가 언제 실행되는지 정의 할 수 있다.
    Class<?>[] groups()  default {};

    // 유효성 검사에 전달한 payload 정의 할 수 있다.
    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        BoardTypeAnnotation[] value();
    }

}
