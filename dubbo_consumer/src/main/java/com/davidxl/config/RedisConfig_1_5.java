package com.davidxl.config;

import com.davidxl.config.properties.MyRedisProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xianglei on 2018/4/27.
 */


@Configuration
@EnableCaching
public class RedisConfig_1_5 extends CachingConfigurerSupport {
    @Autowired
    private MyRedisProperties myRedisProperties;


    @SuppressWarnings("rawtypes")
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);

        //启用前缀
        rcm.setUsePrefix(true);
        RedisCachePrefix cachePrefix = new RedisPrefix(myRedisProperties.getRedisPrefix());
        rcm.setCachePrefix(cachePrefix);

        // 多个缓存的名称,目前只定义了一个
        //设置缓存过期时间(秒)
        rcm.setDefaultExpiration(myRedisProperties.getDefaultExpirationTime());

        //分别设置cache过期时间,单位秒;
        Map<String,Long> expiresMap=new HashMap<>();

        List<String> list = myRedisProperties.getExpiresMap();
        for (String str : list) {
            expiresMap.put(str.split(":")[0],new Long(str.split(":")[1]));
        }
//        expiresMap.put("users",30L);
//        expiresMap.put("products",5L);
        rcm.setExpires(expiresMap);
        return rcm;

    }
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);


        // 设置key的序列化器
        //template.setKeySerializer(new StringRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append("_" + method.getName());
                for (Object obj : params) {
                    sb.append("_" + obj.toString());
                }
                System.out.println("keyGenerator=" + sb.toString());
                return sb.toString();
            }
        };
    }


}