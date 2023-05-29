package com.example.board.dto;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class BoardRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private Integer boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;


    private LocalDate dueDate;
    @Size(min=1, max = 100,
    message = "About Me must be between 10 and 100 characters")
    private String boardType;

}
