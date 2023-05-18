package com.example.board.controller;

import com.example.board.dto.BoardRequest;
import com.example.board.dto.BoardResponse;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api2")
public class BoardController {
    private final Logger log = LoggerFactory.getLogger("BoardController");
    private final BoardService boardService;        // 생성자 주입

/*    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }*/

    @PostMapping("/board")
    public BoardRequest save(@ModelAttribute BoardRequest boardRequest) {
        log.info("boardRequest = " + boardRequest);

        BoardRequest boardModel = BoardRequest.builder()
                .boardTitle(boardRequest.getBoardTitle())
                .boardContents(boardRequest.getBoardContents())
                .boardHits(boardRequest.getBoardHits())
                .boardWriter(boardRequest.getBoardWriter())
                .boardPass(boardRequest.getBoardPass())
                .build();

        boardService.save(boardModel);
        return boardModel;
    }


    @GetMapping("/board")
    public List<BoardResponse> findAll() {
        List<BoardResponse> boardDTOList = boardService.findAll();
        return boardDTOList;
    }

    @GetMapping("/board/{id}")
    public BoardResponse findById(@PathVariable Long id) {
        /*  해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html 에 출력
         */
        boardService.updateHits(id);
        BoardResponse boardResponse = boardService.findById(id);

        return boardResponse;
    }

/*    @PutMapping("/board/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardResponse boardResponse = boardService.findById(id);
        model.addAttribute("boardUpdate", boardResponse);
        return "update";
    }*/

    @PutMapping("/board/{id}")
    public BoardResponse update(@ModelAttribute BoardRequest boardRequest) {
        BoardResponse update = boardService.update(boardRequest);
        return update;
    }


    @DeleteMapping ("/board/{id}")
    public void delete(@PathVariable Long id) {
        boardService.delete(id);
    }
}
