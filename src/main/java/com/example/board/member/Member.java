package com.example.board.member;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Member {

    private Long id;

    @NotEmpty
    private String loginId;

    private String name;
    @NotEmpty
    private String password;

}
