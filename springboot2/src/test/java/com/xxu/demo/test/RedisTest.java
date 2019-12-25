 package com.xxu.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import com.xxu.demo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
 @Autowired
 private RedisTemplate<String, Object> redisTemplate;
 //写入一个字符串
 @Test
 public void testSet(){
	 this.redisTemplate.opsForValue().set("key", "Hello Redis ");
 }
 //读取一个字符串
 @Test
 public void testGet(){
	 String value =	 (String) this.redisTemplate.opsForValue().get("key");
	 System.out.println(value);
 }
 //保存一个User对象(JDK序列化器)
 @Test
 public void testSetUser(){
	 User user=new User();
	 user.setUsername("灰太狼");
	 user.setLoginName("小灰灰");
	 user.setSex('M');
	 user.setAge(18);
	 this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
	 this.redisTemplate.opsForValue().set(user.getUsername(), user);
 }
 //读取一个User对象(JDK序列化器)
 @Test
 public void testGetUser(){
	 this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
	 User user = (User) this.redisTemplate.opsForValue().get("灰太狼");
	 System.out.println(user.toString());
 }
 //保存一个User对象(JSON序列化器)
 @Test
 public void testSetUserByJSON(){
	 User user=new User();
	 user.setUsername("喜洋洋");
	 user.setLoginName("小洋洋");
	 user.setSex('W');
	 user.setAge(15);
	 this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
	 this.redisTemplate.opsForValue().set(user.getUsername(), user);
 }
 //读取一个User对象(JSON序列化器)
 @Test
 public void testGetUserByJSON(){
	 this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(User.class));
	 User user = (User) this.redisTemplate.opsForValue().get("喜洋洋");
	 System.out.println(user.toString());
 }
}
