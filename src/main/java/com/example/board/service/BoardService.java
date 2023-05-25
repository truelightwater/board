package com.example.board.service;

import com.example.board.dto.BoardRequest;
import com.example.board.dto.BoardResponse;
import com.example.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor                // 생성자 주입
public class BoardService {
    private final BoardMapper boardMapper;

    public void save(BoardRequest boardRequest) {
        boardMapper.save(boardRequest);
    }

    public List<BoardResponse> findAll() {
        List<BoardResponse> boardMapperAll = boardMapper.findAll();
        return boardMapperAll.stream().collect(Collectors.toList());
    }

    @Transactional
    public void updateHits(Long id) {
        // 클라이언트 유저의 IP로 한번만 될 수 있도록 하기
        // HashMap 에 IP 체크 (class or Object)
        //
        //
        boardMapper.updateHits(id);
    }

    public BoardResponse findById(Long id) {
        return Optional.ofNullable(boardMapper.findById(id))
                .orElse(null);
    }

    public BoardResponse update(BoardRequest boardRequest) {
        boardMapper.update(boardRequest);
        return findById(boardRequest.getId());
    }

    public void delete(Long id) {
        boardMapper.deleteById(id);
    }
}
