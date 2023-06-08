package com.example.board.server.post.controller;

import com.example.board.aop.annotation.SessionCheck;
import com.example.board.server.post.PostReturn;
import com.example.board.server.post.service.PostService;
import com.example.board.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{postId}")
    public PostReturn getPost(@PathVariable Long postId) {
        PostReturn post = postService.getPost(postId);


        return post;
    }

    @GetMapping
    public List<PostReturn> getPosts(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "5") Integer count) {

        return postService.getPosts(page, count);
    }

    @SessionCheck
    @PostMapping
    public ResponseEntity addPost(@RequestBody @Validated PostForm postForm, HttpSession session) {
        Long memberId = SessionUtils.getSessionValue(session);
        postService.addPost(memberId, postForm.getTitle(), postForm.getContent());
        return ResponseEntity.ok().build();
    }

    @SessionCheck
    @DeleteMapping("/{postId}")
    public ResponseEntity removePost(@PathVariable Long postId, HttpSession session) {
        Long memberId = SessionUtils.getSessionValue(session);

        postService.removePost(memberId, postId);

        return ResponseEntity.noContent().build();
    }

    @SessionCheck
    @PatchMapping("/{postId}")
    public ResponseEntity editPost(@PathVariable Long postId, @RequestBody PostForm postForm, HttpSession session) {
        Long memberId = SessionUtils.getSessionValue(session);
        postService.editPost(memberId, postId, postForm.getTitle(), postForm.getContent());

        return ResponseEntity.ok().build();
    }

    @SessionCheck
    @GetMapping("/my-posts")
    public List<PostReturn> getMyPosts(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "5") Integer count,
                                       HttpSession session) {
        Long memberId = SessionUtils.getSessionValue(session);
        List<PostReturn> posts = postService.getPostsByMemberId(memberId, page, count);

        return posts;
    }



    @Getter
    static class PostForm {
        @NotBlank(message = "제목은 비어있을 수 없습니다.")
        private String title;
        @NotBlank(message = "내용은 비어있을 수 없습니다.")
        private String content;
    }
}
