package com.example.board.service;

import com.example.board.mapper.MemberMapper;
import com.example.board.member.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberMapper memberMapper;

    public Member login(String loginId, String password) {
        return memberMapper.findLoginMember(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }

}
