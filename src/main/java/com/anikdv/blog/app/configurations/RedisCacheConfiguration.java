package com.anikdv.blog.app.configurations;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * Configuring Redis Cache
 *
 * @author anikdv
 */

@Configuration
@EnableCaching
public class RedisCacheConfiguration {

	@Bean
	LettuceConnectionFactory connectionFactory() {
		RedisProperties properties = properties();

		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(properties.getHost());
		configuration.setPort(properties.getPort());

		return new LettuceConnectionFactory(configuration);
	}

	/**
	 * @return RedisTemplate
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory());
		template.setKeySerializer(new JdkSerializationRedisSerializer());
		template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
//		template.setHashKeySerializer(new JdkSerializationRedisSerializer());
//		template.setValueSerializer(new JdkSerializationRedisSerializer());
//		template.setEnableTransactionSupport(true);
		return template;
	}

	@Bean
	@Primary
	RedisProperties properties() {
		return new RedisProperties();
	}

}
