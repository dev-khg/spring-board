package com.example.board.server.post.dao;

import com.example.board.server.post.PostReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisPostDao {
    private final RedisTemplate<String, PostReturn> redisTemplate;

    public void setValues(String key, PostReturn data) {
        ValueOperations<String, PostReturn> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public List<PostReturn> setValuesList(String key, PostReturn data) {
        Long len = redisTemplate.opsForValue().size(key);
        return len == 0 ? new ArrayList<>() : redisTemplate.opsForList().range(key, 0, len);
    }

    public void setValues(String key, PostReturn data, Duration duration) {
        ValueOperations<String, PostReturn> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public PostReturn getValues(String key) {
        ValueOperations<String, PostReturn> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
