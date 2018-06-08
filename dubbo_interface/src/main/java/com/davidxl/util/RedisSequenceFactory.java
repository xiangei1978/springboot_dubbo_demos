package com.davidxl.util;


import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

@Component
public class RedisSequenceFactory {
//    @Autowired
//    RedisTemplate<String, String> mRedisTemp
    private String  redis_key;


    @Autowired
    StringRedisTemplate stringRedisTemplate ;



    /**
     * @Title: set
     * @Description: set cache.
     * @param key
     * @param value
     * @param expireTime
     */
    public void set(String key,int value,Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.set(value);
        counter.expireAt(expireTime);
    }

    /**
     * @Title: set
     * @Description: set cache.
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public void set(String key,int value,long timeout,TimeUnit unit) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.set(value);
        counter.expire(timeout, unit);
    }

    /**
     * @Title: generate
     * @Description: Atomically increments by one the current value.
     * @param key
     * @return
     */
    public long generate(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        return counter.incrementAndGet();
    }

    /**
     * @Title: generate
     * @Description: Atomically increments by one the current value.
     * @param key
     * @return
     */
    public long generate(String key,Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.incrementAndGet();
    }

    /**
     * @Title: generate
     * @Description: Atomically adds the given value to the current value.
     * @param key
     * @param increment
     * @return
     */
    public long generate(String key,int increment) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        return counter.addAndGet(increment);
    }

    /**
     * @Title: generate
     * @Description: Atomically adds the given value to the current value.
     * @param key
     * @param increment
     * @param expireTime
     * @return
     */
    public long generate(String key,int increment,Date expireTime) {
        RedisAtomicLong counter = new RedisAtomicLong(key, stringRedisTemplate.getConnectionFactory());
        counter.expireAt(expireTime);
        return counter.addAndGet(increment);
    }
}