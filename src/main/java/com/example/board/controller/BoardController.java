package com.example.board.controller;

import com.example.board.model.BoardRequest;
import com.example.board.model.BoardResponse;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionCode;
import com.example.board.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Api(description = "게시판 REST API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;      // 생성자 주입
    private static HttpHeaders headers = new HttpHeaders();

    @GetMapping("/test")
    public String test() {
        throw new CustomException(ExceptionCode.NOT_FOUND);

        // RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // ApplicationContext..!
        // Spring 에서 내부 구현방식
    }

    @ApiOperation(value = "게시글 작성")
    @PostMapping("/v1/boards")
    public ResponseEntity<BoardRequest> save(@Valid @ModelAttribute BoardRequest boardRequest) {
        log.info("boardRequest = " + boardRequest);

        // Header 에 필드 추가해보기
        HttpHeaders headers = getResponseHttpHeaders();

        BoardRequest boardModel = BoardRequest.builder()
                .boardTitle(boardRequest.getBoardTitle())
                .boardContents(boardRequest.getBoardContents())
                .boardWriter(boardRequest.getBoardWriter())
                .boardPass(boardRequest.getBoardPass())
                .build();

        boardService.save(boardModel);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(boardModel);
    }


    @ApiOperation(value = "게시글 전체목록 조회")
    @GetMapping("/v1/boards")
    public ResponseEntity<List<BoardResponse>> findAll() {
        HttpHeaders headers = getResponseHttpHeaders();

        List<BoardResponse> boardDTOList = boardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(boardDTOList);
    }


    @ApiOperation(value = "게시글 상세내용 조회")
    @GetMapping("/v1/boards/{id}")
    public ResponseEntity<BoardResponse> findById
            (@PathVariable("id") @ApiParam(value = "게시글 번호") Long id, HttpServletRequest request) {

        // 찾는 아이디가 없다면, NOT_FOUND 를 발생시킨다.
        BoardResponse byId = boardService.findById(id);
        if (byId == null) {
            throw new CustomException(ExceptionCode.NOT_FOUND);
        }

        //  해당 게시글의 조회수를 하나 올리고 반환한다.
        boardService.updateHits(id, request);

        // 비즈니스 로직
        BoardResponse response = boardService.findById(id);
        return ResponseEntity.ok(response);

/*        try {
            boardService.updateHits(id);
            BoardResponse boardResponse = boardService.findById(id);

            return ResponseEntity.ok(boardResponse);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 게시글이 없습니다.");
        }*/
    }

    @ApiOperation(value = "게시글 상세 내용 수정")
    @PutMapping("/v1/boards/{id}")
    public ResponseEntity<BoardResponse> update(@ModelAttribute BoardRequest boardRequest) {

        BoardResponse update = boardService.update(boardRequest);
        return ResponseEntity.ok(update);
    }

    @ApiOperation(value = "게시글 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping ("/v1/boards/{id}")
    public void delete(@PathVariable("id") @ApiParam(value = "게시글 번호")Long id) {
        boardService.delete(id);
    }

    private static HttpHeaders getResponseHttpHeaders() {
        // 새로운 Response 헤더 필드 추가
        headers.add("CodeWriter", "kks");
        return headers;
    }
}
