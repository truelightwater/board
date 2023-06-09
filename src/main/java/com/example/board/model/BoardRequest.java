package com.example.board.model;

import com.example.board.validation.BoardTypeAnnotation;
import com.example.board.validation.PassWordConfirmCheck;
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
@BoardTypeAnnotation(boardType = "boardType", dueDate = "dueDate")
//@PassWordConfirmCheck(boardPass = "boardPass", boardPassConfirm = "boardPassConfirm")
public class BoardRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "작성자는 필수값입니다.")
    private String boardWriter;
    private String boardPass;
    private String boardPassConfirm;
    private String boardTitle;
    private String boardContents;
    private Integer boardHits = 0;
    private LocalDateTime boardCreatedTime = LocalDateTime.now();
    private LocalDateTime boardUpdatedTime;
    private LocalDate dueDate;
    private BoardTypes boardType;

}
