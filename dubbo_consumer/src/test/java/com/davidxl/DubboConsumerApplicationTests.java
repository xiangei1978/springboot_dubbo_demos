package com.davidxl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DubboConsumerApplicationTests {
	@Autowired
	RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
		log.info("sha1:"+DigestUtils.sha1Hex("123456 "));
	}

	@Test
	public void put(){

		redisTemplate.opsForValue().set("test001","test001");
		Assert.assertEquals("test001", redisTemplate.opsForValue().get("test001"));
	}

}
