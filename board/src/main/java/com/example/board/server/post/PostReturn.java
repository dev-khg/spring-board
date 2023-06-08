package com.example.board.server.post;

import com.example.board.server.post.dto.Post;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@ToString
public class PostReturn implements Serializable {
    private Long id;
    private String title;
    private String content;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime registerDate;
    private String nickname;
    private int views;

    protected  PostReturn() { }

    PostReturn(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.registerDate = post.getRegisterDate();
        this.views = post.getViews();
    }

    public void increaseView() {
        this.views++;
    }

    public static PostReturn create(Post post) {
        return new PostReturn(post);
    }
}
