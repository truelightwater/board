package com.example.board.model;

import com.example.board.validation.BoardTypeAnnotation;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "작성자는 필수값입니다.")
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private Integer boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private LocalDate dueDate;          // 마감일
    @BoardTypeAnnotation(boardTypeCheck = BoardTypes.NOTYPE)
    private BoardTypes boardType;       // 게시글 타입


    public void setDueDate(LocalDate localDate) {
        this.dueDate = localDate;
    }
}
