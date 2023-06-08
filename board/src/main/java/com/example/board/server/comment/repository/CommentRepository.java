package com.example.board.server.comment.repository;

import com.example.board.mapper.CommentMapper;
import com.example.board.server.comment.CommentReturn;
import com.example.board.server.comment.dto.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final CommentMapper commentMapper;

    public boolean addComment(Comment comment) {
        return commentMapper.insert(comment) == 1;
    }

    public boolean editComment(Long commentId, String content, int score) {
        return commentMapper.update(commentId, score, content) == 1;
    }

    public boolean deleteById(Long commentId) {
        return commentMapper.delete(commentId) == 1;
    }

    public List<CommentReturn> getComments(Long postId, Integer offset, Integer count) {
        return commentMapper.getCommentsByPostId(postId, offset, count);
    }

    public CommentReturn getCommentById(Long commentId) {
        return commentMapper.getCommentById(commentId);
    }

    public boolean isExistByMemberIdAndPostId(Long memberId, Long commentId) {
        return commentMapper.getCount(memberId, commentId) == 1;
    }
}
