package com.example.board;


import com.example.board.dto.BoardResponse;
import com.example.board.mapper.BoardMapper;
import com.example.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @InjectMocks
    BoardService boardService;

    @Mock
    BoardMapper boardMapper;

    @Test
    @DisplayName("전체 게시글 조회")
    void findAll() {
        // given

    }

}
