package com.example.board.controller;

import com.example.board.dto.BoardRequest;
import com.example.board.dto.BoardResponse;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final Logger log = LoggerFactory.getLogger("BoardController");

    private final BoardService boardService;        // 생성자 주입

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    // @ModelAttribute : boardDTO 클래스를 찾아서 필드값(html)이 동일하다면
    // 해당하는 필드의 Setter 를 호출해서 Setter 메소드에 담아준다.
    public String save(@ModelAttribute BoardRequest boardRequest) {
        System.out.println("boardDTO = " + boardRequest);
        boardService.save(boardRequest);

        return "index";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        // DB 에서 전체 게시글 데이터를 가져와서 list.html 에 보여준다.
        List<BoardResponse> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);

        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        /*  해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html 에 출력
         */
        boardService.updateHits(id);
        BoardResponse boardResponse = boardService.findById(id);
        model.addAttribute("board", boardResponse);

        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardResponse boardResponse = boardService.findById(id);
        model.addAttribute("boardUpdate", boardResponse);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardRequest boardRequest, Model model) {
        BoardResponse update = boardService.update(boardRequest);
        model.addAttribute("board", update);
        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/";
    }
}
