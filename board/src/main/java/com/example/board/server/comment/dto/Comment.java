package com.example.board.server.comment.dto;

import com.example.board.server.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Comment {
    private Long id;
    private String content;
    private int score;
    private Long memberId;
    private Long postId;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private Status status;

    protected Comment() { }

    protected Comment(Long postId, Long memberId, int score, String content) {
        this.content = content;
        this.memberId = memberId;
        this.postId = postId;
        this.status = Status.USING;
        this.registerDate = LocalDateTime.now();
        this.score = score;
    }

    public static Comment createComment(Long postId, Long memberId, String content, int score) {
        return new Comment(postId, memberId, score, content);
    }
}
