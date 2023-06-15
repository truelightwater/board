package com.example.board;

import com.example.board.controller.BoardController;
import com.example.board.service.BoardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ValidatorTest {

    private MockMvc mockMvc;
    private BoardService boardService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BoardController(boardService)).build();
    }

    @Test
    public void matchingPassword() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/boards")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("boardContent", "TEST")
                        .param("boardWriter", "TEST")
                        .param("boardPass", "1234")
                        .param("boardPassConfirm", "1234")
                        .param("boardTitle", "테스트입니다."))
                .andExpect(status().isOk());
    }






}
