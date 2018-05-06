package com.davidxl.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@ConfigurationProperties(prefix="project.swagger")
//@PropertySource("classpath:swagger2.properties")
public class SwaggerProperties {

    private String title;
    private String basePackage;
    private String contactName;
    private String description;
    private String version;
//    private String termsOfServiceUrl;
//    private String contactEmail;
    private String contactUrl;

}
