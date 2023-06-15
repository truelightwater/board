package com.example.board.exception2;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    @ApiModelProperty(example = "에러 발생시간")
    private final LocalDateTime timestamp = LocalDateTime.now();

    @ApiModelProperty(example = "에러설명")
    private final String error;

    @ApiModelProperty(example = "상태코드")
    private final int code;

    @ApiModelProperty(example = "메세지")
    private final String message;
}
