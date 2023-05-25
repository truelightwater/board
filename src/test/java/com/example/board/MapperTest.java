package com.example.board;

import com.example.board.dto.BoardRequest;
import com.example.board.dto.BoardResponse;
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
        BoardRequest params = new BoardRequest();
        params.setBoardTitle("테스트 제목 4");
        params.setBoardContents("테스트 내용 4");
        params.setBoardWriter("테스트 글쓴이 4");
        params.setBoardPass("1234");
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
        BoardResponse byId = boardMapper.findById(1L);

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
        BoardRequest params = new BoardRequest();
        params.setId(2L);
        params.setBoardTitle("수정 제목 2");
        params.setBoardContents("수정 내용 2");
        params.setBoardWriter("수정자 변경 2");

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
