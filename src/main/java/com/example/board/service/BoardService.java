package com.example.board.service;

import com.example.board.model.BoardRequest;
import com.example.board.model.BoardResponse;
import com.example.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor                // 생성자 주입
public class BoardService {
    private final BoardMapper boardMapper;
    private final DataSource dataSource;
    private Set<String> uniqueIPs = new HashSet<>();

    public void save(BoardRequest boardRequest) {
        boardMapper.save(boardRequest);
    }

    public List<BoardResponse> findAll() {
        List<BoardResponse> boardMapperAll = boardMapper.findAll();
        return boardMapperAll.stream().collect(Collectors.toList());
    }


    public void updateHits(Long id, HttpServletRequest request) throws SQLException {
        // 클라이언트 유저의 IP로 한번만 될 수 있도록 하기
        // HashMap 에 IP 체크 (class or Object)

        String clientIP = request.getRemoteAddr();  // 요청자의 IP
        log.info("clientIP = {}", clientIP);

        TransactionSynchronizationManager.initSynchronization();
        Connection con = DataSourceUtils.getConnection(dataSource);

        con.setAutoCommit(false);

        try {

            if (!uniqueIPs.contains(clientIP)) {
                uniqueIPs.add(clientIP);
                boardMapper.updateHits(id);
            }

            // Thread.sleep(10000);
            con.commit();

        } catch (Exception e) {
            con.rollback();
            throw new IllegalStateException(e);
        } finally {
            DataSourceUtils.releaseConnection(con, dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }

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
