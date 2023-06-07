package com.example.board.mapper;

import com.example.board.server.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    Member findById(Long id);
    int countByLoginId(String loginId);
    int countByNickname(String nickname);
    Member findByLoginIdAndPassword(@Param("loginId") String loginId, @Param("password") String password);
    int insert(Member member);
    int delete(Long memberId);
}
