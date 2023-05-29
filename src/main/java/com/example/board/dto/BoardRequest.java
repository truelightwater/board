package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class BoardRequest {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private Integer boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;




}
