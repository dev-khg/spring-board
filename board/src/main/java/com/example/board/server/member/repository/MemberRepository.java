package com.example.board.server.member.repository;

import com.example.board.mapper.MemberMapper;
import com.example.board.server.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final MemberMapper memberMapper;

    public Member findById(Long memberId) {
        return memberMapper.findById(memberId);
    }

    public boolean isExistLoginId(String loginId) {
        return memberMapper.countByLoginId(loginId) != 0;
    }

    public boolean isExistNickname(String nickname) {
        return memberMapper.countByNickname(nickname) != 0;
    }

    public Member findByLoginIdAndPassword(String loginId, String password) {
        return memberMapper.findByLoginIdAndPassword(loginId, password);
    }

    public boolean delete(Long userId) {
        return memberMapper.delete(userId) == 1;
    }

    public boolean insert(Member member) {
        return memberMapper.insert(member) == 1;
    }
}
