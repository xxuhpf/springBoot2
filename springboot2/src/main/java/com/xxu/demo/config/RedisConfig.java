package com.xxu.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	/**
	 * 1.创建JedisPoolConfig对象。在该对象中完成一些链接池配置
	 *
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.redis.jedis.pool")
	public JedisPoolConfig jedisPoolConfig(){
		JedisPoolConfig config = new JedisPoolConfig();
		//最大空闲数
		config.setMaxIdle(10);
		//最小空闲数
		config.setMinIdle(5);
		//最大链接数
		config.setMaxTotal(20);
		System.out.println(config.getMaxIdle());
		return config;
	}
	
	/**
	 * 2.创建JedisConnectionFactory：配置redis链接信息
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.redis")
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig config){
		System.out.println(config.getMaxIdle());
		JedisConnectionFactory factory = new JedisConnectionFactory();
		//关联链接池的配置对象
		factory.setPoolConfig(config);

		//配置链接Redis的信息
		//主机地址
		factory.setHostName("127.0.0.1");
		//端口
		factory.setPort(6379);
		//设置要操作的库(默认操作第1个库)
		factory.setDatabase(0);
		return factory;
	}
	
	/**
	 * 3.创建RedisTemplate:用于执行Redis操作的方法
	 */
	@Bean
	public RedisTemplate<String,Object> redisTemplate(JedisConnectionFactory factory){
		//String是key的类型，object是value的类型
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		//关联
		template.setConnectionFactory(factory);
		//为key设置序列化器
		template.setKeySerializer(new StringRedisSerializer());
		//为value设置序列化器
		template.setValueSerializer(new StringRedisSerializer());
		return template;
	}
}
