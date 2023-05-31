package com.example.board.controller;

import com.example.board.model.BoardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
public class ResponseBodyController {

    @GetMapping("response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @GetMapping("response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<BoardResponse> responseBodyJsonV1() {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setBoardWriter("글쓴이");
        boardResponse.setBoardTitle("제목입니다.");

        return new ResponseEntity<>(boardResponse, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/response-body-json-v2")
    public BoardResponse responseBodyJsonV2() {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setBoardWriter("글쓴이");
        boardResponse.setBoardTitle("제목입니다.");

        return boardResponse;

    }
}
