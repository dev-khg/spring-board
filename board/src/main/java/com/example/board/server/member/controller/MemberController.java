package com.example.board.server.member.controller;

import com.example.board.aop.annotation.SessionCheck;
import com.example.board.server.member.dto.Member;
import com.example.board.server.member.repository.MemberRepository;
import com.example.board.server.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.board.utils.SessionUtils.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLogin memberLogin, HttpSession session) {
        Long memberId = memberService.login(memberLogin.getLoginId(), memberLogin.getPassword());
        createSession(session, memberId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid MemberSign memberSign, HttpSession session) {
        Long memberId = memberService.signUp(memberSign.getLoginId(), memberSign.getPassword(), memberSign.getNickname());
        createSession(session, memberId);

        return ResponseEntity.ok().build();
    }

    @SessionCheck
    @DeleteMapping("/sign-out")
    public ResponseEntity signOut(@RequestBody @Valid String password, HttpSession session) {
        Long memberId = getSessionValue(session);
        memberService.signOut(memberId, password);
        invalidateSession(session);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity logOut(HttpSession session) {
        invalidateSession(session);

        return ResponseEntity.ok().build();
    }



    @Getter
    static class MemberLogin {
        @NotEmpty(message = "로그인 아이디를 입력해주세요.")
        private String loginId;
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
    }

    @Getter
    static class MemberSign {
        @NotEmpty(message = "닉네임을 입력해주세요.")
        private String nickname;
        @NotEmpty(message = "로그인 아이디를 입력해주세요.")
        private String loginId;
        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
    }
}
