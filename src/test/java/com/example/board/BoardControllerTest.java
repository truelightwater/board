package com.example.board;

import com.example.board.controller.BoardController;
import com.example.board.model.BoardRequest;
import com.example.board.model.BoardResponse;
import com.example.board.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BoardController.class)
public class BoardControllerTest {

    // "Spring Bean" Jackson, 직렬, 역직렬 유사한 빈 있는지 확인하기
    // https://www.baeldung.com/spring-boot-customize-jackson-objectmapper
    ObjectMapper objectMapper = new ObjectMapper();

    // Mock vs MockBean
    @MockBean
    BoardService boardService;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Joda ? LocalDate ? Year ?
        // JavaTimeModule()을 왜 불편함이 생겼을까 ?
        // LocalDate... ! Java 8
        // Jackson 은 왜 지원하지 않을까 ?
        // Opensource 확인해보기
        // Spring 에서는 어떻게 사용하는지, 어떻게 돌아가는것인지
        objectMapper.registerModule(new JavaTimeModule());
    }


    @Test
    @DisplayName("[POST]게시글 작성")
    public void saveBoardTest() {
        // given
            // json 파일로 만들어서 String 으로 변수담아서 진행
            // 메소드 추출처럼 Fixed, 해도 괜찮다.
            // 재사용
        BoardRequest boardRequest = getBoardRequest();
        // json 처리를 다해서 진행
        // json 파일을 바로 읽어서 진행
        // 문자열 그대로 가져오는게 좋다.
        // 어떻게 json 파일을 가져오는지

        try {
            String boardToString = objectMapper.writeValueAsString(boardRequest);

            // when
            ResultActions resultActions = mockMvc.perform(post("/api/v1/boards")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(boardToString));

            // then
            resultActions
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        /**
         * boardService 는 리턴값이 void 이므로 given() 및 willReturn() 부분이 없이
         * verify()를 사용하여 boardService.save() 메서드가 지정된 매개변수로 한 번 호출되었는지 확인
         */
        verify(boardService, times(1)).save(any(BoardRequest.class));

    }

    private static BoardRequest getBoardRequest() {
        BoardRequest boardRequest = BoardRequest.builder()
                        .id(1L)
                        .boardTitle("글쓰기 테스트")
                        .boardContents("테스트입니다.")
                        .boardWriter("김경수")
                        .boardPass("1234")
                        .build();
        return boardRequest;
    }

    @Test
    @DisplayName("[GET]게시글 전체 조회")
    public void findAllBoardTest() throws Exception {
        // given


        // final 키워드 : 변경되지 않은 것은 final,
        // 불변에 대한 생각
        // ImmutableList -> Read-Only 명시적으로 가독성면에서 좋다.
        // https://www.techiedelight.com/mutable-unmodifiable-immutable-empty-list-java/
        final List<BoardResponse> responseList = List.of();


        responseList.add(BoardResponse.builder()
                .id(1L)
                .boardTitle("제목1")
                .boardContents("내용1")
                .boardWriter("글쓴이1")
                .boardPass("12341")
                .build());

        responseList.add(BoardResponse.builder()
                .id(2L)
                .boardTitle("제목2")
                .boardContents("내용2")
                .boardWriter("글쓴이2")
                .boardPass("12342")
                .build());

        given(boardService.findAll()).willReturn(responseList);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseList)));

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].boardTitle").value("제목1"))
                .andExpect(jsonPath("$[1].boardContents").value("내용2"))
                .andExpect(jsonPath("$[0].boardWriter").value("글쓴이1"))
                .andExpect(jsonPath("$[1].boardPass").value("12342"));
    }

    @Test
    @DisplayName("[GET]게시글 일부 조회")
    public void findOneBoardTest() throws Exception {
        // given
        BoardResponse boardResponse = getBoardResponse();

        given(boardService.findById(1L)).willReturn(boardResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/boards/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardResponse)));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("boardTitle").value("제목1"))
                .andExpect(jsonPath("boardContents").value("내용1"))
                .andExpect(jsonPath("boardWriter").value("글쓴이1"))
                .andExpect(jsonPath("boardPass").value("12341"));

    }

    private static BoardResponse getBoardResponse() {
        BoardResponse boardResponse = BoardResponse.builder()
                .id(1L)
                .boardTitle("제목1")
                .boardContents("내용1")
                .boardWriter("글쓴이1")
                .boardPass("12341")
                .build();
        return boardResponse;
    }

    @Test
    @DisplayName("[PUT]게시글 수정")
    public void updateBoardTest() throws Exception {
        // given
        BoardResponse boardResponse = BoardResponse.builder()
                .boardTitle("수정후 제목")
                .boardContents("수정후 내용")
                .boardWriter("수정후 글쓴이")
                .build();

        // boardRequest 객체 생성해서 진행 -> any()
        // equals, hashcode 어떻게 동작하는지
        // 특정타입을 넣어서 같은지
        given(boardService.update(any())).willReturn(boardResponse);

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/v1/boards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardResponse)));
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("boardTitle").value("수정후 제목"))
                .andExpect(jsonPath("boardContents").value("수정후 내용"))
                .andExpect(jsonPath("boardWriter").value("수정후 글쓴이"));
    }

    @Test
    @DisplayName("[DELETE]게시글 삭제")
    public void deleteBoardTest() throws Exception {
        // given
        Long id = 11L;

        // when
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/boards/{id}", id));

        // then
        resultActions
                .andExpect(status().isOk());

        verify(boardService, times(1)).delete(11L);
    }
}
