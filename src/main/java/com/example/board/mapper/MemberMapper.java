package com.example.board.mapper;

import com.example.board.member.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    // 회원정보 저장
    void saveMember(Member member);

    // 회원정보 조회
    Member findByMember(Long id);

    // 회원정보 전체 조회
    List<Member> findAllMember();

    // 로그인 회원정보 조회
    Optional<Member> findLoginMember(String loginId);

    // 회원정보 삭제
    void deleteByMember(Long id);

    // 회원정보 전체 삭제
    void deleteTable();


}
