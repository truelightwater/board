package com.example.board;

import com.example.board.model.BoardRequest;
import com.example.board.model.BoardResponse;
import com.example.board.mapper.BoardMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
// @MybatisTest 단위테스트 코드
// layer 에 맞는 단위테스트가 필요하다.
//
@SpringBootTest
public class MapperTest {

    @Autowired
    BoardMapper boardMapper;

    @Test
    void save() {
        BoardRequest params = BoardRequest.builder()
                        .boardTitle("테스트 제목 4")
                        .boardContents("테스트 내용")
                        .boardWriter("글쓴이 4")
                        .boardPass("1234")
                        .build();

        boardMapper.save(params);


    }

    @Test
    void findAll() {
        List<BoardResponse> list = boardMapper.findAll();
        System.out.println(list.size());

        Assertions.assertThat(list).isInstanceOf(list.getClass());
    }

    @Test
    void findById() {
        BoardResponse byId = boardMapper.findById(39L);

        try {
            String value = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(byId);
            System.out.println(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        // 1. 게시글 수정

        BoardRequest params = BoardRequest.builder()
                .id(2L)
                .boardTitle("수정 제목 2")
                .boardContents("수정 내용 2")
                .boardWriter("수정자 2")
                .build();

        boardMapper.update(params);

        // 2. 게시글 상세정보 조회
        BoardResponse byId = boardMapper.findById(2L);

        try {
            String value = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(byId);
            System.out.println(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete() {
        boardMapper.deleteById(4L);
    }

}
