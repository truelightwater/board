package com.example.board.controller;

import com.example.board.exception.errorstatus.NotFoundException;
import com.example.board.mapper.MemberMapper;
import com.example.board.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberMapper memberMapper;

    /**
     * 회원 저장
     * @param member
     * @param result
     * @return
     */
    @PostMapping("/v1/member")
    public ResponseEntity<Member> save(@Validated @ModelAttribute Member member, BindingResult result) {

        if (result.hasErrors()) {
            log.error("검증 오류 발생 errors = {}", result);
        }

        log.info("member save = {}", member);

        memberMapper.saveMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    /**
     * 전체 회원조회
     * @return
     */
    @GetMapping("/v1/members")
    public ResponseEntity<List<Member>> findAll() {
        List<Member> allMember = memberMapper.findAllMember();
        return ResponseEntity.status(HttpStatus.OK).body(allMember);
    }

    /**
     * 회원 아이디로 찾기
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/v1/members/{id}")
    public ResponseEntity<Member> findById(@PathVariable("id") Long id, HttpServletRequest request) {

        Member member = memberMapper.findByMember(id);
        if (member == null) {
            log.error("찾는 회원이 없습니다.");
            throw new NotFoundException();
        }

        return ResponseEntity.ok(member);
    }


    /**
     * 로그인 회원 찾기
     * @param loginId
     * @param request
     * @return
     */

    @GetMapping("/v1/members/login/{loginId}")
    public ResponseEntity<Optional<Member>> findLoginMember(@PathVariable("loginId") String loginId, HttpServletRequest request) {
        Optional<Member> loginMember = memberMapper.findLoginMember(loginId);

        return ResponseEntity.ok(loginMember);
    }


    /**
     * 회원 삭제
     * @param id
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/v1/members/{id}")
    public void delete(@PathVariable("id") Long id) {
        memberMapper.deleteByMember(id);
    }
}
