package com.example.board.server.comment.controller;

import com.example.board.aop.annotation.SessionCheck;
import com.example.board.common.exception.InvalidRequestRuntimeException;
import com.example.board.server.comment.CommentReturn;
import com.example.board.server.comment.service.CommentService;
import com.example.board.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentController {
    private final CommentService commentService;

    @SessionCheck
    @PostMapping("/{postId}/comments")
    public ResponseEntity addComment(@PathVariable Long postId,
                                     @RequestBody CommentForm commentForm,
                                     HttpSession session) {
        Long memberId = SessionUtils.getSessionValue(session);
        commentForm.checkValid();
        commentService.addComment(postId, memberId, commentForm.getContent(), commentForm.getScore());

        return ResponseEntity.ok().build();
    }

    @SessionCheck
    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity editComment(@PathVariable Long postId, @PathVariable Long commentId,
                                      @RequestBody CommentForm commentForm, HttpSession session) {
        Long memberId = SessionUtils.getSessionValue(session);
        commentForm.checkValid();

        commentService.checkAuthor(memberId, commentId);
        commentService.editComment(commentId, commentForm.getContent(), commentForm.getScore());

        return ResponseEntity.ok().build();
    }

    @SessionCheck
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity deletePost(@PathVariable Long postId, @PathVariable Long commentId , HttpSession session) {
        Long memberId = SessionUtils.getSessionValue(session);
        commentService.checkAuthor(memberId, commentId);
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}/comments")
    public List<CommentReturn> getComments(@PathVariable Long postId,
                                           @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer count) {
        return commentService.getComments(postId, page, count);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public CommentReturn getComment(@PathVariable Long postId, @PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    @Getter
    static class CommentForm {
        private String content;
        private Integer score;

        void checkValid() {
            if(!StringUtils.hasText(content) || score == null) {
                throw new InvalidRequestRuntimeException(HttpStatus.BAD_REQUEST, "내용 혹은 평점을 입력해주세요.");
            }
        }
    }
}
