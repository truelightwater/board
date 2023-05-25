package com.example.board.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;


@Getter
@ToString
public enum ExceptionCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "권한이 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "서버가 요청을 거부하고 있습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "요청받은 리소스를 찾을수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405, "허용되지 않은 메소드 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버에 오류가 발생하여 요청을 수행할수 없습니다.");

    private final HttpStatus status;
    private final int code;
    private String message;

    ExceptionCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
