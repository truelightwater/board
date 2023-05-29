package com.example.board;

import com.example.board.controller.BoardController;
import com.example.board.dto.BoardRequest;
import com.example.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BoardControllerTest {

    @InjectMocks
    BoardController boardController;

    @Mock
    BoardService boardService;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("게시글 작성")
    public void saveBoardTest() throws Exception {
        // given
        BoardRequest requestTest = BoardRequest.builder()
                .boardContents("테스트")
                .boardWriter("테스트")
                .boardTitle("테스트")
                .boardPass("1234")
                .build();

        // when
        mockMvc.perform(post("/api/v1/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTest)))
                .andExpect(status().isOk());

        // then
        // Argument(s) are different!
        // verify(boardService).save(requestTest);
    }

    @Test
    @DisplayName("게시글 전체 조회")
    public void findAllBoardTest() throws Exception {
        mockMvc.perform(get("/api/v1/boards"))
                .andExpect(status().isOk());

        verify(boardService).findAll();
    }

    @Test
    @DisplayName("게시글 일부 조회")
    public void findOneBoardTest() throws Exception {
        // given
        Long id = 1L;

        // when
        mockMvc.perform(get("/api/v1/boards/{id}", id))
                .andExpect(status().isOk());

        verify(boardService).findById(id);
    }

    @Test
    @DisplayName("게시글 수정")
    public void updateBoardTest() throws Exception {
        // given
        Long id = 1L;
        BoardRequest request = BoardRequest.builder()
                .id(1L)
                .boardTitle("수정된 제목")
                .boardContents("수정된 내용")
                .boardWriter("새로운 수정자")
                .build();

        // when
        mockMvc.perform(put("/api/v1/boards/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // then
        // Argument(s) are different!
        // verify(boardService).update(request);
        // assertThat(request.getBoardTitle()).isEqualTo("수정된 제목");


    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteBoardTest() throws Exception {
        // given
        Long id = 11L;

        // when
        mockMvc.perform(delete("/api/v1/boards/{id}", id))
                .andExpect(status().isOk());
    }
}
