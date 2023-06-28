package com.example.board.mapper;

import com.example.board.member.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface IpMapper {

    // IP 저장
    void save(String address);

    // IP 데이터 유무
    boolean exist(String address);

    // IP 조회
    String findByIp(String address);





}
