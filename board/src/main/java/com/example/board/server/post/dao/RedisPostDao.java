package com.example.board.server.post.dao;

import com.example.board.server.post.PostReturn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
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

    @Scheduled(cron = "1 * * * * ?")
    public void applyViewCountToRDB() {
        Set<String> keys = redisTemplate.keys("*");

        for (String key : keys) {
            log.info(getValues(key).toString());
        }
    }
}
