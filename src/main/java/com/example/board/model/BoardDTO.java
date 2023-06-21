package com.example.board.model;


import com.example.board.validation.BoardTypeAnnotation;
import com.example.board.validation.PassWordConfirmCheck;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
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
@PassWordConfirmCheck.List({
        @PassWordConfirmCheck(
                boardPass = "boardPass",
                boardPassConfirm= "boardPassConfirm")
})
@BoardTypeAnnotation.List({
        @BoardTypeAnnotation(
                boardType = "boardType",
                dueDate = "dueDate")
})
public class BoardDTO {

    private Long id;

    @NotNull(message = "작성자는 필수값입니다.")
    private String boardWriter;

    @Size(min = 4, max= 12)
    private String boardPass;

    @Size(min = 4, max= 12)
    private String boardPassConfirm;

    @NotBlank
    private String boardTitle;

    @NotBlank
    private String boardContents;
    private Integer boardHits = 0;
    private LocalDateTime boardCreatedTime = LocalDateTime.now();
    private LocalDateTime boardUpdatedTime;
    private LocalDate dueDate;
    private BoardTypes boardType;
}
