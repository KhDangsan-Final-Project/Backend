package com.ms1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import jakarta.annotation.PostConstruct;

@Configuration
public class RedisConfig {

    private final RedisConnectionFactory connectionFactory;

    public RedisConfig(RedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @PostConstruct
    public void testRedisConnection() {
        try {
            String result = connectionFactory.getConnection().ping();
            System.out.println("Redis Connection Test: " + result);
        } catch (Exception e) {
            System.err.println("Redis Connection Failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
