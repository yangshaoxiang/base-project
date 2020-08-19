package com.ysx.common.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 *  spring cache 配置 配置自定义缓存 key 生成策略
 */
@Configuration
public class CacheConfig {
    /**
     *  该值是 keyGenerator 方法的方法名称，如果Bean 指定了名称，则使用指定的名称
     */
    public static final String KEY_GENERATOR = "springCacheKeyGenerator";

    /**
     *  在 CacheConfig 中定义
     */
    @Bean(KEY_GENERATOR)
    KeyGenerator springCacheKeyGenerator(){
        return new SpringCacheKeyGenerator();
    }


    /**
     *  redis cache 配置
     *  1. 配置序列化策略 解决可视化工具查看乱码问题
     *  2. 配置 不存储 null 值
     *  3. 配置全局失效时间
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // redis cache 配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                      // 序列化配置
                      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                      // 不缓存 null 值
                     .disableCachingNullValues()
                      // 全局缓存时间
                     .entryTtl(Duration.ofMinutes(15));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(config)
                .build();
    }


}
