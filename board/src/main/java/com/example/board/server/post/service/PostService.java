package com.example.board.server.post.service;

import com.example.board.common.exception.InvalidRequestRuntimeException;
import com.example.board.common.exception.UnAuthorizationRuntimeException;
import com.example.board.server.post.PostReturn;
import com.example.board.server.post.dao.RedisPostDao;
import com.example.board.server.post.dto.Post;
import com.example.board.server.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final RedisPostDao redisPostDao;

    public PostReturn getPost(Long postId) {
        PostReturn postReturn = getRedisPost(postId);
        increaseView(postReturn);
        return postReturn;
    }

    public List<PostReturn> getPosts(Integer page, Integer count) {
        return postRepository.findPosts(page - 1, count);
    }

    public List<PostReturn> getPostsByMemberId(Long memberId, Integer page, Integer count) {
        return postRepository.findPostsByMemberId(memberId, page - 1, count);
    }

    @Transactional
    public void addPost(Long memberId, String title, String content) {
        Post post = Post.createPost(memberId, title, content);

        boolean isSuccess = postRepository.save(post);

        if (!isSuccess) {
            throw new RuntimeException("포스팅 생성 실패");
        }
    }

    public void editPost(Long memberId, Long postId, String title, String content) {
        checkAuthor(memberId, postId);
        checkTextFormat(title, content);

        boolean result = postRepository.update(postId, title, content);

        if (!result) {
            throw new RuntimeException("수정할 수 없습니다.");
        }

    }

    public void removePost(Long memberId, Long postId) {
        checkAuthor(memberId, postId);
        postRepository.deleteById(postId);
    }

    private void checkAuthor(Long memberId, Long postId) {
        if (!postRepository.isExistByMemberIdAndPostId(memberId, postId)) {
            throw new UnAuthorizationRuntimeException(HttpStatus.FORBIDDEN, "존재하지 않거나, 권한이 없는 페이지입니다.");
        }
    }

    private void checkTextFormat(String... str) {
        for (String item : str) {
            if (StringUtils.hasText(item)) {
                return;
            }
        }
        throw new InvalidRequestRuntimeException(HttpStatus.BAD_REQUEST, "공백은 입력할 수 없습니다.");
    }

    public PostReturn getRedisPost(Long postId) {
        PostReturn value = redisPostDao.getValues("post:" + postId.toString());
        if(value == null) {
            value = postRepository.findPostById(postId);
            if (value == null) {
                throw new NoSuchElementException("해당 게시물이 없거나 삭제되었습니다.");
            }
        }
        redisPostDao.setValues("post:" + postId.toString(), value);
        return value;
    }

    public void increaseView(PostReturn postReturn) {
        postReturn.increaseView();

        redisPostDao.setValues("post:" + postReturn.getId().toString(), postReturn);

        log.info("==== VALUE ==== ");
        log.info(postReturn.toString());
        log.info("=============== ");
    }

    @ToString
    @RedisHash(value = "post")
    static class RedisPost implements Serializable {
        private Long id;
        private Integer viewCount;

        private RedisPost(Long postId, Integer viewCount) {
            this.id = postId;
            this.viewCount = viewCount;
        }

        protected RedisPost() {
            this.viewCount = 0;
        }

        public void increaseViewCount() {
            this.viewCount++;
        }

        public static RedisPost create(Long postId, Integer viewCount) {
            return new RedisPost(postId, viewCount);
        }
    }

}
