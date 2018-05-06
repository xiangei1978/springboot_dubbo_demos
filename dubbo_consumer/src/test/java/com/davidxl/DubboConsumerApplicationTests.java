package com.davidxl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DubboConsumerApplicationTests {
	@Autowired
	RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void put(){

		redisTemplate.opsForValue().set("test001","test001");
		Assert.assertEquals("test001", redisTemplate.opsForValue().get("test001"));
	}

}
