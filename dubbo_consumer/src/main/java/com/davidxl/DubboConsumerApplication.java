package com.davidxl;

import com.davidxl.config.properties.KaptchaProperties;
import com.davidxl.web.ResponseBodyWrapFactoryBean;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.davidxl"})
public class DubboConsumerApplication extends SpringBootServletInitializer  implements CommandLineRunner {

	@Autowired
	KaptchaProperties kaptchaProperties;

	public static void main(String[] args) {
		SpringApplication.run(DubboConsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("服务调用者------>>启动完毕");
	}

	@Bean
	public DefaultKaptcha getDefaultKaptcha(){
		com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", kaptchaProperties.getBorder());
		properties.setProperty("kaptcha.border.color",kaptchaProperties.getBorder_color());
		properties.setProperty("kaptcha.textproducer.font.color", kaptchaProperties.getTextproducer_font_color());
		properties.setProperty("kaptcha.image.width", kaptchaProperties.getImage_width());
		properties.setProperty("kaptcha.image.height", kaptchaProperties.getImage_height());
		properties.setProperty("kaptcha.textproducer.font.size", kaptchaProperties.getTextproducer_font_size());
		properties.setProperty("kaptcha.session.key", kaptchaProperties.getSession_key());
		properties.setProperty("kaptcha.textproducer.char.length", kaptchaProperties.getTextproducer_char_length());
		properties.setProperty("kaptcha.textproducer.font.names", kaptchaProperties.getTextproducer_font_names());
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);

		return defaultKaptcha;
	}

	@Bean
	public ResponseBodyWrapFactoryBean getResponseBodyWrap() {
		return new ResponseBodyWrapFactoryBean();
	}
}
