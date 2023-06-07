package com.example.board.server.member.dto;

import com.example.board.server.member.enums.MemberStatus;
import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private String loginId;
    private String password;
    private String nickname;
    private String registerDate;
    private MemberStatus status;

    protected Member() { }
}
