package com.lockers.outerpark.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * RedisTemplate<String, String> 설정
     *
     * Redis 분산락 구현을 위해 key, value 모두 String 타입으로 구성합니다.
     * Key   : "lock:concert:{concertId}:seat:{seatId}"
     *   -> 좌석 별 락을 구분하기 위한 고유 식별자
     * Value : "a8f1-23bc-9aa3-44d0" <- 이거는 UUID 값입니다.
     *   -> 해당 락을 소유한 요청자의 고유 값
     *
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
}
