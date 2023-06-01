package com.example.board.model;

public enum BoardTypes {
    QUESTION("질문"),
    ANSWER("답변"),
    NOTICE("공지"),
    QUIZ("퀴즈"),
    NOTYPE("없음");

    private final String Type;

    BoardTypes(String type) {
        Type = type;
    }
}
