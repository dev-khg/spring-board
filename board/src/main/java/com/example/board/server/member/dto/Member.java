package com.example.board.server.member.dto;

import com.example.board.server.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Member {

    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    private LocalDateTime registerDate;
    private Status status;

    protected Member() { }

    private Member(String loginId, String password, String nickname)
    {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.registerDate = LocalDateTime.now();
        this.status = Status.USING;
    }

    public static Member createMember(String loginId, String password, String nickname) {
        return new Member(loginId, password, nickname);
    }
}
