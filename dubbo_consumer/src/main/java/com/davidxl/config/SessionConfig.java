package com.davidxl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Administrator
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3000)
public class SessionConfig {
}
