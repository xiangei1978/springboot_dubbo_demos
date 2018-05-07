package com.davidxl.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "project.web", ignoreInvalidFields = true)
public class WebProperties {

    private List<String> uriIgnore;
}
