package com.davidxl;

import com.davidxl.web.ResponseBodyWrapFactoryBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.davidxl"})
public class DubboConsumerApplication extends SpringBootServletInitializer  implements CommandLineRunner {



	public static void main(String[] args) {
		SpringApplication.run(DubboConsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println("服务调用者------>>启动完毕");
	}

	@Bean
	public ResponseBodyWrapFactoryBean getResponseBodyWrap() {
		return new ResponseBodyWrapFactoryBean();
	}
}
