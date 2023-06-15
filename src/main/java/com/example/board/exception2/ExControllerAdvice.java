package com.example.board.exception2;

import com.example.board.exception2.resposestatus.ForbiddenException;
import com.example.board.exception2.resposestatus.NotFoundException;
import com.example.board.exception2.resposestatus.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.web.servlet.function.ServerResponse.status;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse illegalExHandler(IllegalArgumentException ex) {
        log.error("400 error", ex);

        return new ErrorResponse("Bad Request Exception", 400, "잘못된 값을 입력하였습니다.");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> unauthorized(UnauthorizedException ex) {
        log.error("401 error", ex);

        ErrorResponse errorResponse = new ErrorResponse("Unauthorized Exception!", 401, "인증에 실패하였습니다.");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> forbidden(ForbiddenException ex) {
        log.error("403 error", ex);

        ErrorResponse errorResponse = new ErrorResponse("Forbidden Exception!", 403, "권한이 없습니다.");

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notFound(NotFoundException ex) {
        log.error("404 error", ex);

        ErrorResponse errorResponse = new ErrorResponse("NotFound Exception!", 404, "조회한 게시글이 없습니다.");

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }







    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResponse exHandler(Exception ex) {
        log.error("500 error", ex);
        return new ErrorResponse("Exception!", 500, "예외가 발생했습니다.");
    }

}
