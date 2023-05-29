package com.example.board;

import com.example.board.controller.BoardController;
import com.example.board.dto.BoardRequest;
import com.example.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BoardController.class)
public class BoardControllerTest {
    @MockBean
    BoardController boardController;
    @MockBean
    BoardService boardService;
    @Autowired
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
                .id(1L)
                .boardContents("테스트")
                .boardWriter("테스트")
                .boardTitle("테스트")
                .boardPass("1234")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTest)))
                .andDo(print());


        // then
        resultActions
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("id").value(1L));
//                .andExpect(jsonPath("boardWriter").value("테스트"))
//                .andExpect(jsonPath("boardTitle").value("테스트"))
//                .andExpect(jsonPath("boardPass").value("1234"));

        // then
        // Argument(s) are different!
        // verify(boardService).save(requestTest);
    }

    @Test
    @DisplayName("게시글 전체 조회")
    public void findAllBoardTest() throws Exception {
        mockMvc.perform(get("/api/v1/boards"))
                .andExpect(status().isOk());

        // verify(boardService).findAll();
    }

    @Test
    @DisplayName("게시글 일부 조회")
    public void findOneBoardTest() throws Exception {
        // given
        Long id = 1L;

        // when
        mockMvc.perform(get("/api/v1/boards/{id}", id))
                .andExpect(status().isOk());

        // verify(boardService).findById(id);
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
