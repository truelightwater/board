package com.example.board.controller;

import com.example.board.exception2.resposestatus.ForbiddenException;
import com.example.board.exception2.resposestatus.NotFoundException;
import com.example.board.exception2.resposestatus.UnauthorizedException;
import com.example.board.model.BoardRequest;
import com.example.board.model.BoardResponse;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionCode;
import com.example.board.service.BoardService;
import io.swagger.annotations.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Api(description = "게시판 REST API")
@RestController
@RequiredArgsConstructor
@Builder
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;      // 생성자 주입
    private static HttpHeaders headers = new HttpHeaders();   // Http Headers

    @GetMapping("/test")
    public String test() {
        throw new CustomException(ExceptionCode.NOT_FOUND);

        // RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // ApplicationContext..!
        // Spring 에서 내부 구현방식
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 201, message = "게시글 생성"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 403, message = "권한실패"),
            @ApiResponse(code = 404, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiOperation(value = "게시글 작성", notes = "게시판 글을 작성합니다.")
    @PostMapping("/v1/boards")
    public ResponseEntity<BoardRequest> save(@ModelAttribute @Valid BoardRequest boardRequest) {
        log.info("boardRequest = " + boardRequest);

        // Header 에 필드 추가해보기
        HttpHeaders headers = getResponseHttpHeaders();

        BoardRequest boardModel = BoardRequest.builder()
                .id(boardRequest.getId())
                .boardTitle(boardRequest.getBoardTitle())
                .boardContents(boardRequest.getBoardContents())
                .boardWriter(boardRequest.getBoardWriter())
                .boardPass(boardRequest.getBoardPass())
                .boardPassConfirm(boardRequest.getBoardPassConfirm())
                .boardHits(boardRequest.getBoardHits())
                .boardCreatedTime(boardRequest.getBoardCreatedTime())
                .boardUpdatedTime(boardRequest.getBoardUpdatedTime())
                .dueDate(boardRequest.getDueDate())
                .boardType(boardRequest.getBoardType())
                .build();

        boardService.save(boardModel);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(boardModel);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 403, message = "권한실패"),
            @ApiResponse(code = 404, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiOperation(value = "게시글 전체목록 조회", notes = "게시글 전체를 조회하여 보여줍니다.")
    @GetMapping("/v1/boards")
    public ResponseEntity<List<BoardResponse>> findAll() {
        HttpHeaders headers = getResponseHttpHeaders();

        List<BoardResponse> boardDTOList = boardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(boardDTOList);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 403, message = "권한실패"),
            @ApiResponse(code = 404, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiOperation(value = "게시글 상세내용 조회", notes = "하나의 게시글을 찾아서 보여줍니다.")
    @GetMapping("/v1/boards/{id}")
    public ResponseEntity<BoardResponse> findById
            (@PathVariable("id") @ApiParam(value = "게시글 번호") Long id, HttpServletRequest request) {

        // 찾는 아이디가 없다면, NOT_FOUND 를 발생시킨다.
        BoardResponse byId = boardService.findById(id);
        if (byId == null) {
            log.error("찾는 아이디가 없습니다.");
            throw new NotFoundException();
        }

        //  해당 게시글의 조회수를 하나 올리고 반환한다.
        boardService.updateHits(id, request);

        // 비즈니스 로직
        BoardResponse response = boardService.findById(id);
        return ResponseEntity.ok(response);

    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 403, message = "권한실패"),
            @ApiResponse(code = 404, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiOperation(value = "게시글 상세 내용 수정", notes = "게시글을 수정할 수 있습니다.")
    @PutMapping("/v1/boards/{id}")
    public ResponseEntity<BoardResponse> update(@ModelAttribute @Valid BoardRequest boardRequest) {

        // 인증 체크
        boolean isAuthorized = checkUserAuthorization();
        boolean hasPermission = checkUserPermission();


        if (isAuthorized) {
            if (hasPermission) {
                BoardResponse update = boardService.update(boardRequest);
                return ResponseEntity.ok(update);
            } else {
                throw new ForbiddenException();
            }
        } else {
            throw new UnauthorizedException();
        }

    }

    private boolean checkUserAuthorization() {
        // 인증 실패라고 가정
        return true;
    }

    private boolean checkUserPermission() {
        // 권한 실패라고 가정
        return true;
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 403, message = "권한실패"),
            @ApiResponse(code = 404, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
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
