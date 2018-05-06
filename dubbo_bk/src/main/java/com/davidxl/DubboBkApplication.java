package com.davidxl;

import com.davidxl.config.properties.MyRocketmqProperties;
import com.davidxl.web.ResponseBodyWrapFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.davidxl"})
//@EnableConfigurationProperties({MyRocketmqProperties.class})
@MapperScan("com.davidxl.dao")
public class DubboBkApplication extends SpringBootServletInitializer  implements CommandLineRunner {



	public static void main(String[] args) {
		SpringApplication.run(DubboBkApplication.class, args);
		try {
			TimeUnit.MINUTES.sleep(10); //提供者main线程暂停10分钟等待被调用
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.err.println("服务提供者------>>服务关闭");
	}


	@Override
	public void run(String... args) throws Exception {
		System.err.println("服务提供者------>>启动完毕");
	}
	@Bean
	public ResponseBodyWrapFactoryBean getResponseBodyWrap() {
		return new ResponseBodyWrapFactoryBean();
	}
}
