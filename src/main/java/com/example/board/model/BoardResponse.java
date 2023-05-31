package com.example.board.model;

import lombok.*;

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
    private Long id;                            // PK
    private String boardTitle;                  // 제목
    private String boardContents;               // 내용
    private String boardWriter;                 // 작성자
    private String boardPass;                   // 비밀번호
    private int boardHits;                      // 조회 수
    private LocalDateTime boardCreatedTime;     // 생성일시
    private LocalDateTime boardUpdatedTime;     // 최종 수정일시
    private LocalDate dueDate;                  // 마감일
    @Size(min=1, max = 10,
            message = "About Me must be between 10 and 100 characters")
    private String boardType;                   // 타입

}
