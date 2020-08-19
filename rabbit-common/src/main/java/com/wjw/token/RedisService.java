package com.wjw.token;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : wjwjava01@163.com
 * @date : 19:39 2020/8/19
 * @description :
 */
@Service
public class RedisService {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置key,value和过期时间
     *
     * @param key        key
     * @param value      value
     * @param expireTime expire time
     * @return
     */
    public boolean setExpire(String key, Object value, Long expireTime) {
        try {
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            ops.set(key, value);
            Boolean expire = redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            if (Objects.nonNull(expire)) {
                return expire;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断key是否存在
     * @param key k
     * @return
     */
    public boolean exists(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        if (Objects.nonNull(hasKey)) {
            return hasKey;
        }
        return false;
    }

    public boolean delete(String key){
        Boolean result = redisTemplate.delete(key);
        if (Objects.nonNull(result)) {
            return result;
        }
        return false;
    }
}
