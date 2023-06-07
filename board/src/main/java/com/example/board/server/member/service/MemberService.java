package com.example.board.server.member.service;

import com.example.board.common.exception.DuplicatedException;
import com.example.board.common.exception.NoResourceException;
import com.example.board.common.exception.UnAuthorizationException;
import com.example.board.server.member.dto.Member;
import com.example.board.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long login(String loginId, String password) {
        Member member = memberRepository.findByLoginIdAndPassword(loginId, password);
        if (member == null) {
            throw new NoResourceException(HttpStatus.BAD_REQUEST, "아이디 혹은 비밀번호가 일치하지 않습니다.");
        }

        return member.getId();
    }

    public void signOut(Long memberId, String password) {
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new UnAuthorizationException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }
        memberRepository.delete(memberId);
    }

    public Long signUp(String loginId, String password, String nickname) {
        if (memberRepository.isExistLoginId(loginId)) {
            throw new DuplicatedException(HttpStatus.BAD_REQUEST, "중복된 아이디가 있습니다.");
        }
        if (memberRepository.isExistNickname(nickname)) {
            throw new DuplicatedException(HttpStatus.BAD_REQUEST, "중복된 닉네임이 있습니다.");
        }

        Member member = Member.createMember(loginId, password, nickname);

        boolean isSuccess = memberRepository.insert(member);

        if (!isSuccess) {
            log.error("[Exception] loginId = {}, password = {}, nickname = {}", loginId, password, nickname);
            throw new RuntimeException("알 수 없는 예외입니다.");
        }
        return member.getId();
    }
}
