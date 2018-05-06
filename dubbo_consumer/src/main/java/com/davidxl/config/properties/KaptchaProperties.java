package com.davidxl.config.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix=KaptchaProperties.PREFIX)
public class KaptchaProperties {
    public static final String PREFIX = "project.kaptcha";

   private String border;
   private String border_color;
   private String textproducer_font_color;
   private String image_width;
   private String image_height;
   private String textproducer_font_size;
   private String session_key;
   private String textproducer_char_length;
   private String textproducer_font_names;

}
