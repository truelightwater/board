package com.example.board.controller;

import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.Map;


@ApiIgnore
@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;
    private static HttpHeaders headers = new HttpHeaders();

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        HttpHeaders headers = getResponseHttpHeaders();

        // 응답생성
        String responseBody = "Hello, World";

        return ResponseEntity.ok().headers(headers).body(responseBody);
    }


    @GetMapping("/test2")
    public String test2(@RequestHeader HttpHeaders requestHeader) {
        return "header : " + requestHeader.getAccept();
    }


    private static HttpHeaders getResponseHttpHeaders() {

        // 새로운 Response 헤더 필드 추가
        headers.add("Content-Language", "ko-KR");
        headers.add("Character-Set", "UTF-8");
        return headers;
    }
}
