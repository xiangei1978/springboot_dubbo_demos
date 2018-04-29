package com.davidxl.config;


import lombok.extern.slf4j.Slf4j;

//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheWriter;


/**
 * Created by xianglei on 2018/4/23.
 */
@Slf4j
//@Configuration
//@EnableCaching
/**
 *
 *
 *
 * springboot 2.0 对应redis 配置
 *
 * */
public class RedisConfig  {
//public class RedisConfig extends CachingConfigurerSupport {
//    private transient final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
//
//    private @Autowired
//    RedisExpireProperties redisExpireProperties;
//
//
//    @Bean
//    CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        //user信息缓存配置
//        RedisCacheConfiguration userCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().
//                                                        entryTtl(Duration.ofSeconds(10)).disableCachingNullValues().prefixKeysWith("users_");
//        //product信息缓存配置
//        RedisCacheConfiguration productCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().
//                                                        entryTtl(Duration.ofMinutes(1)).disableCachingNullValues().prefixKeysWith("products_");
//        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
//        redisCacheConfigurationMap.put("users", userCacheConfiguration);
//        redisCacheConfigurationMap.put("products", productCacheConfiguration);
//        //初始化一个RedisCacheWriter
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
//
//
//        //设置CacheManager的值序列化方式为JdkSerializationRedisSerializer,但其实RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key，JdkSerializationRedisSerializer序列化value,所以以下注释代码为默认实现
//        //ClassLoader loader = this.getClass().getClassLoader();
//        //JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
//        //RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);
//        //RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
//
//
//        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
//        //设置默认超过期时间是30秒
//        defaultCacheConfig.entryTtl(Duration.ofSeconds(30));
//        //初始化RedisCacheManager
//        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig, redisCacheConfigurationMap);
//        return cacheManager;
//    }
//

}