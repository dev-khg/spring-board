package com.example.board.server.member.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.board.utils.SessionUtils.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody MemberLogin memberLogin, HttpSession session) {
        // logic
        Long memberId = 0L;
        //
        createSession(session, memberId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody @Valid MemberSign memberSign, HttpSession session) {
        Long memberId = 0L;

        // logic

        //
        createSession(session, memberId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity signOut(@RequestBody @Valid String password) {

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
