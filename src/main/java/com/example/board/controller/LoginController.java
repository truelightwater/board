package com.example.board.controller;

import com.example.board.member.Member;
import com.example.board.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final String LOGIN_MEMBER = "loginMember";

    @PostMapping("/v1/login")
    public ResponseEntity<Member> login(@Validated @ModelAttribute Member member,
                                        BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            log.error("bindingResult 에러");
        }

        Member loginMember = loginService.login(member.getLoginId(), member.getPassword());
        log.info("login = {}", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        // 로그인 성공
        HttpSession session = request.getSession();

        // 세션에 로그인 회원정보 보관
        session.setAttribute(LOGIN_MEMBER, loginMember);



        return ResponseEntity.ok().body(loginMember);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/v1/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
            log.info("로그아웃 성공");
        }

    }
}
