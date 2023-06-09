package com.example.board;

import com.example.board.model.BoardRequest;
import com.example.board.validation.PassWordConfirmCheckValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

public class ValidatorTest {

    BoardRequest boardRequest;
    PassWordConfirmCheckValidator ps;
    ConstraintValidatorContext context;

    @Test
    void 비밀번호_확인() {
        boardRequest = new BoardRequest();

        boardRequest.setBoardPass("1234");
        boardRequest.setBoardPassConfirm("12");

        ps.isValid(boardRequest, context);

        Assertions.assertThat(boardRequest.getBoardPass())
                .isEqualTo(boardRequest.getBoardPassConfirm());

    }


}
