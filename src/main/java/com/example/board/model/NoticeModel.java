package com.example.board.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoticeModel {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regDate;
}
