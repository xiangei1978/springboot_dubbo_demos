package com.davidxl.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@Data
@ConfigurationProperties(prefix="project.quartz")
public class QuartzProperties {

    private List<String> jobs;

}
