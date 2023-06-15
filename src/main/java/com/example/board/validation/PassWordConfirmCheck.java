package com.example.board.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Constraint(validatedBy = PassWordConfirmCheckValidator.class)
public @interface PassWordConfirmCheck {

    String message() default "비밀번호와 비밀번호 확인이 일치하지 않습니다.";
    String boardPass();
    String boardPassConfirm();

    // 유효성 검사가 언제 실행되는지 정의 할 수 있다.
    Class<?>[] groups()  default {};

    // 유효성 검사에 전달한 payload 정의 할 수 있다.
    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        PassWordConfirmCheck[] value();
    }
}
