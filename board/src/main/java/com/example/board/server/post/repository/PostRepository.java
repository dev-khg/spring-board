package com.example.board.server.post.repository;

import com.example.board.mapper.PostMapper;
import com.example.board.server.post.PostReturn;
import com.example.board.server.post.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final PostMapper postMapper;

    public PostReturn findPostById(Long postId) {
        return postMapper.findById(postId);
    }

    public List<PostReturn> findPosts(Integer offset, Integer count) {
        return postMapper.findPosts(offset, count);
    }

    public List<PostReturn> findPostsByMemberId(Long memberId, Integer offset, Integer count) {
        return postMapper.findPostsByMemberId(memberId, offset, count);
    }

    public boolean isExistByMemberIdAndPostId(Long memberId, Long postId) {
        return postMapper.findByMemberIdAndPostId(memberId, postId) == 1;
    }

    public boolean save(Post post) {
        return postMapper.insert(post) == 1;
    }

    public boolean update(Long postId, String title, String content) {
        return postMapper.update(postId, title, content) == 1;
    }

    public boolean deleteById(Long postId) {
        return postMapper.delete(postId) == 1;
    }
}
