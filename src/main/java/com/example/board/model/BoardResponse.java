package com.example.board.model;

import com.example.board.validation.BoardTypeAnnotation;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;                            // PK
    private String boardTitle;                  // 제목
    private String boardContents;               // 내용
    private String boardWriter;                 // 작성자
    private String boardPass;                   // 비밀번호
    private int boardHits;                      // 조회 수
    private LocalDateTime boardCreatedTime;     // 생성일시
    private LocalDateTime boardUpdatedTime;     // 최종 수정일시
    private LocalDate dueDate;                  // 마감일
    @BoardTypeAnnotation(boardTypeCheck = BoardTypes.NOTYPE)
    private BoardTypes boardType;               // 게시글 타입

    public void setDueDate(LocalDate localDate) {
        this.dueDate = localDate;
    }

}
