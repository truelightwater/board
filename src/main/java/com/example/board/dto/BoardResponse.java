package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class BoardResponse {
    private Long id;                       // PK
    private String boardTitle;                  // 제목
    private String boardContents;                // 내용
    private String boardWriter;                 // 작성자
    private String boardPass;
    private int boardHits;                   // 조회 수
    private LocalDateTime boardCreatedTime;     // 생성일시
    private LocalDateTime boardUpdatedTime;    // 최종 수정일시

}
