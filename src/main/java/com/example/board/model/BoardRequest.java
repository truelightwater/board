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

// 401, 404 에러에 대한 정의
// Swagger 문서에 정의를 하기 ( 에러 정의 )
// Exception 에 대한 설계
// 어떻게 Bean Validation 을 하는지


// 인증, 인가에 대한 구분
// Spring Security = 어떤 패턴이면 어떤 룰셋이 일치하라
// 메뉴얼로 만든다. => 응답정의

public class BoardRequest {

    // Pojo 기반
    // 서비스에 준한 Object
    // 다른 클래스 오브젝트로 넘겨야 한다.
    // boardDTO 클래스
    // 책임이 너무 많아지게 된다. ( = 책임의 분리 )
    // 유틸성 Mapper Class
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 언제 & 어떻게 동작하는지에 대한 이해
    // 어떤 예외를 Exception 이 되는지 확인해야한다.
    // 어느 타이밍에 되는지
    // API 문서화에 대한 고민 -> 내가 개발했으니깐, 설명할줄 알아야 한다.
    // API 에 대한 에러에 대한 고민, 이해가 있어야 설명이 가능하다.
    // Spring 의 활용도가 높아야 한다.
    // API 스펙, Query Param, Bean Validation
    // HTTP Message Converter
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
