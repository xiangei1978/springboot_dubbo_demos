package com.davidxl.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix=JWTProperties.PREFIX)
public class JWTProperties {
    public static final String PREFIX = "project.jwt";

   private String SigningKey;

}
