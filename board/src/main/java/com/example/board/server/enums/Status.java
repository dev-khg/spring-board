package com.example.board.server.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public enum Status {
    USING("U"),
    DELETED("D"),
    BLOCKED("B"),
    SLEEP("S");

    private String value;

    Status(String value) {
        this.value = value;
    }

}
