package com.example.board.mapper;


import com.example.board.server.post.PostReturn;
import com.example.board.server.post.dto.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostMapper {
    PostReturn findById(Long id);
    List<PostReturn> findPosts(@Param("offset") Integer offset, @Param("count") Integer count);
    int insert(Post post);
    int update(@Param("postId") Long postId,@Param("title") String title, @Param("content") String content);
    int delete(Long postId);
    int findByMemberIdAndPostId(@Param("memberId") Long memberId, @Param("postId") Long postId);
    List<PostReturn> findPostsByMemberId(@Param("memberId") Long memberId, @Param("offset") Integer offset, @Param("count") Integer count);
}
