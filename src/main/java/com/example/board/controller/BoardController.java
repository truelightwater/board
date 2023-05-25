package com.example.board.controller;

import com.example.board.dto.BoardRequest;
import com.example.board.dto.BoardResponse;
import com.example.board.exception.CustomException;
import com.example.board.exception.ExceptionCode;
import com.example.board.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import java.util.List;

@Slf4j
@Api(description = "게시판 REST API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;      // 생성자 주입

    @GetMapping("/test")
    public String test() {
        throw new CustomException(ExceptionCode.NOT_FOUND);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // ApplicationContext..!
        // Spring 에서 내부 구현방식
        //


    }

/*    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }*/
    @ApiOperation(value = "게시글 작성")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/v1/boards")
    public ResponseEntity<BoardRequest> save(@ModelAttribute BoardRequest boardRequest) {
        log.info("boardRequest = " + boardRequest);

        BoardRequest boardModel = BoardRequest.builder()
                .boardTitle(boardRequest.getBoardTitle())
                .boardContents(boardRequest.getBoardContents())
                .boardHits(boardRequest.getBoardHits())
                .boardWriter(boardRequest.getBoardWriter())
                .boardPass(boardRequest.getBoardPass())
                .build();

        boardService.save(boardModel);
        return ResponseEntity.ok(boardModel);
    }


    @ApiOperation(value = "게시글 전체목록 조회")
    @GetMapping("/v1/boards")
    public ResponseEntity<List<BoardResponse>> findAll() {
        List<BoardResponse> boardDTOList = boardService.findAll();
        return ResponseEntity.ok(boardDTOList);
    }


    @ApiOperation(value = "게시글 상세내용 조회")
    @GetMapping("/v1/boards/{id}")
    public ResponseEntity<BoardResponse> findById(@PathVariable("id") @ApiParam(value = "게시글 번호") Long id) {

        //찾는 아이디가 없다면, NOT_FOUND 를 발생시킨다.
        BoardResponse byId = boardService.findById(id);

        if (byId == null) {
            throw new CustomException(ExceptionCode.NOT_FOUND);
        }

        //  해당 게시글의 조회수를 하나 올리고 반환한다.
        boardService.updateHits(id);
        BoardResponse boardResponse = boardService.findById(id);

        return ResponseEntity.ok(boardResponse);
/*        try {
            boardService.updateHits(id);
            BoardResponse boardResponse = boardService.findById(id);

            return ResponseEntity.ok(boardResponse);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 게시글이 없습니다.");
        }*/
    }

/*    @PutMapping("/board/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardResponse boardResponse = boardService.findById(id);
        model.addAttribute("boardUpdate", boardResponse);
        return "update";
    }*/

    @ApiOperation(value = "게시글 상세 내용 수정")
    @PutMapping("/v1/boards/{id}")
    public ResponseEntity<BoardResponse> update(@ModelAttribute BoardRequest boardRequest) {

        BoardResponse update = boardService.update(boardRequest);
        return ResponseEntity.ok(update);
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping ("/v1/boards/{id}")
    public ResponseEntity delete(@PathVariable("id") @ApiParam(value = "게시글 번호")Long id) {
        boardService.delete(id);
        return (ResponseEntity) ResponseEntity.ok();
    }
}
