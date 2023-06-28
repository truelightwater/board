package com.example.board.controller;

import com.example.board.exception.errorstatus.PasswordConfirmInvalidException;
import com.example.board.exception.errorstatus.PasswordSizeInvalidException;
import com.example.board.exception.errorstatus.*;
import com.example.board.model.BoardDTO;
import com.example.board.model.BoardRequest;
import com.example.board.model.BoardResponse;
import com.example.board.service.BoardService;
import io.swagger.annotations.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Api(description = "게시판 REST API")
@RestController
@RequiredArgsConstructor
@Builder
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;      // 생성자 주입

    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 201, message = "게시글 생성"),
            @ApiResponse(code = 401, message = "인증실패"),
            @ApiResponse(code = 403, message = "권한실패"),
            @ApiResponse(code = 404, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버에러")
    })
    @ApiOperation(value = "게시글 작성", notes = "게시판 글을 작성합니다.")
    @PostMapping("/v1/board")
    public ResponseEntity<BoardRequest> save(@Validated @ModelAttribute BoardDTO boardDTO,
                                             BindingResult bindingResult) {
        log.info("boardDTO = " + boardDTO);


        // 비밀번호 Validations
        if (bindingResult.hasErrors()) {
            log.error("검증 오류 발생 errors = {}", bindingResult);

            // notification 패턴
            for (ObjectError error : bindingResult.getAllErrors()) {
                if (error instanceof FieldError) {
                    throw new PasswordSizeInvalidException(bindingResult);
                } else if (error instanceof ObjectError) {
                    throw new PasswordConfirmInvalidException(bindingResult);
                }
            }
        }

        // boardDTO => board
        // Boarder border = Bodrder.builder().id(borderDto.getID())

        // Controller
        // borderRequest -> borderDto
        // Service
        // borderDto -> border

        // 성공 로직
        BoardRequest boardModel = BoardRequest.builder()
                .id(boardDTO.getId())
                .boardTitle(boardDTO.getBoardTitle())
                .boardContents(boardDTO.getBoardContents())
                .boardWriter(boardDTO.getBoardWriter())
                .boardPass(boardDTO.getBoardPass())
                .boardPassConfirm(boardDTO.getBoardPassConfirm())
                .boardHits(boardDTO.getBoardHits())
                .boardCreatedTime(boardDTO.getBoardCreatedTime())
                .boardUpdatedTime(boardDTO.getBoardUpdatedTime())
                .dueDate(boardDTO.getDueDate())
                .boardType(boardDTO.getBoardType())
                .build();
        // Mapstruct 찾아보기

        boardService.save(boardModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(boardModel);
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

        List<BoardResponse> boardDTOList = boardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(boardDTOList);
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
            (@PathVariable("id") @ApiParam(value = "게시글 번호") Long id, HttpServletRequest request) throws SQLException {

        // 찾는 아이디가 없다면, NOT_FOUND 를 발생시킨다.
        BoardResponse byId = boardService.findById(id);
        if (byId == null) {
            log.error("찾는 아이디가 없습니다.");
            throw new NotFoundException();
        }

        //  해당 게시글의 조회수를 하나 올리고 반환한다.
        log.info("Controller updateHits");
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
    public ResponseEntity<BoardResponse> update(@Validated @ModelAttribute BoardDTO boardDTO) {

        // 인증 체크
        boolean isAuthorized = checkUserAuthorization();
        boolean hasPermission = checkUserPermission();

        // if(!isQuthrorized) {
        // throw new xxx Exetipn()..}
        if (isAuthorized) {

            if (hasPermission) {

                BoardRequest boardRequest = BoardRequest.builder()
                        .boardTitle(boardDTO.getBoardTitle())
                        .boardContents(boardDTO.getBoardContents())
                        .boardWriter(boardDTO.getBoardWriter())
                        .dueDate(boardDTO.getDueDate())
                        .boardType(boardDTO.getBoardType())
                        .build();

                BoardResponse update = boardService.update(boardRequest);
                return ResponseEntity.ok(update);
            } else { throw new ForbiddenException(); }

        } else { throw new UnauthorizedException(); }

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

}
