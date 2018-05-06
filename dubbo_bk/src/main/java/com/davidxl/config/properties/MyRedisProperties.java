package com.davidxl.config.properties;


import com.davidxl.config.RedisPrefix;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCachePrefix;

import java.util.List;

@Configuration
@Data
@ConfigurationProperties(prefix="spring.redis")
//@PropertySource("classpath:swagger2.properties")
public class MyRedisProperties {
    //缓存过期时间（秒）
    private String redisPrefix;
    private int  defaultExpirationTime;
    private List<String> expiresMap;

}
