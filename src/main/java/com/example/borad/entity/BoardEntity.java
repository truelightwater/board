package com.example.borad.entity;

import com.example.borad.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


// DB의 테이블 역할을 하는 클래스 : 테이블 객체를 구성하게 된다.
// Service, Repository 에서 사용한다.
@Entity
@Getter
@Setter
@Table(name = "board_table")                                    // DB 테이블의 이름을 생성
public class BoardEntity extends BaseEntity {
    @Id                                                         // pk 컬럼 지정, 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // auto_increment (MySQL 기준)
    private Long id;

    @Column(length = 20, nullable = false)                      // 크기 20, not null, 글제목이 꼭 있어야
    private String boardWriter;

    @Column()                                                   // Default : 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    // DTO 에 담긴 값을 Entity 값으로 옮겨 담는 메소드
    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);

        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setId(boardDTO.getId());        // ID 가 있어야 쿼리 실행
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());

        return boardEntity;
    }
}
