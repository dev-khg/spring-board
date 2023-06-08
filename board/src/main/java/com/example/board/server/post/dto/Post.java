package com.example.board.server.post.dto;

import com.example.board.server.enums.Status;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter
public class Post {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime registerDate;
    private LocalDateTime modifyDate;
    private Status status;
    private Long memberId;

    protected Post() { }

    private Post(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.status = Status.USING;
        this.registerDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
    }

    public static Post createPost(Long memberId, String title, String content) {
        if(!StringUtils.hasText(title) || !StringUtils.hasText(content)) {
            throw new IllegalStateException("제목이나 내용은 공백일 수 없습니다.");
        }
        return new Post(memberId, title, content);
    }
}
