package com.example.board.mapper;

import com.example.board.server.comment.Comment;
import com.example.board.server.comment.CommentReturn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    List<CommentReturn> getCommentsByPostId(@Param("postId") Long postId,
                                            @Param("offset") Integer offset,
                                            @Param("count") Integer count);

    CommentReturn getCommentById(Long commentId);

    int insert(Comment comment);

    int update(@Param("commentId") Long commentId,@Param("content") String content);

    int delete(Long commentId);

    int getCount(@Param("memberId") Long memberId, @Param("commentId") Long commentId);
}
