package com.boot;

import com.boot.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

	public void contextLoads() {
	}


	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Test
	public void test() throws Exception {

		/*//利用jaskon进行序列化和反序列化
		ObjectMapper mapper = new ObjectMapper();

		User user = new User("超人", 20);
		redisTemplate.opsForHash().put("user","s",mapper.writeValueAsString(user));

		redisTemplate.opsForHash().delete("user","s");

		System.out.println(redisTemplate.opsForHash().get("user","s"));*/
		/*user = new User("蝙蝠侠", 30);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		user = new User("蜘蛛侠", 40);
		redisTemplate.opsForValue().set(user.getUsername(), user);
		Assert.assertEquals(20, redisTemplate.opsForValue().get("超人").getAge().longValue());
		Assert.assertEquals(30, redisTemplate.opsForValue().get("蝙蝠侠").getAge().longValue());
		Assert.assertEquals(40, redisTemplate.opsForValue().get("蜘蛛侠").getAge().longValue());*/
	}

}
