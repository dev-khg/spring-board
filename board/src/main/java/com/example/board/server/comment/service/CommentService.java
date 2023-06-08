package com.example.board.server.comment.service;

import com.example.board.common.exception.InvalidRequestRuntimeException;
import com.example.board.common.exception.UnAuthorizationRuntimeException;
import com.example.board.server.comment.CommentReturn;
import com.example.board.server.comment.dto.Comment;
import com.example.board.server.comment.repository.CommentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public void addComment(Long postId, Long memberId, String content, int score) {
        Comment comment = Comment.createComment(postId, memberId, content, score);
        boolean success = commentRepository.addComment(comment);
        if(!success) {
            throw new RuntimeException("댓글 삽입 중 오류 발생");
        }
    }

    public void editComment(Long commentId, String content, Integer score) {
        boolean isSuccess = commentRepository.editComment(commentId, content, score);
        if(!isSuccess) {
            throw new RuntimeException("댓글 수정 중 오류 발생");
        }
    }

    public void deleteComment(Long commentId) {
        boolean isSuccess = commentRepository.deleteById(commentId);
        if(!isSuccess) {
            throw new RuntimeException("댓글 삭제 중 오류 발생");
        }
    }

    public List<CommentReturn> getComments(Long postId, Integer page, Integer count) {
        return commentRepository.getComments(postId, page -1, count);
    }

    public CommentReturn getComment(Long commentId) {
        return commentRepository.getCommentById(commentId);
    }

    public void checkAuthor(Long memberId, Long commentId) {
        boolean result = commentRepository.isExistByMemberIdAndPostId(memberId, commentId);
        if(!result) {
            throw new UnAuthorizationRuntimeException(HttpStatus.FORBIDDEN, "존재하지 않는 게시물이거나, 권한이 없습니다.");
        }
    }
}
