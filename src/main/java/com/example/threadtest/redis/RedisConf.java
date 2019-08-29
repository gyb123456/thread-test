package com.example.threadtest.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;
import static com.fasterxml.jackson.annotation.PropertyAccessor.ALL;
import static com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL;

/**
 * @author ：GYB
 * @date ：Created in 2019/8/28 13:32
 * @description：TODO
 * @version: 1.0
 */
@Configuration
public class RedisConf {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(factory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(ALL, NONE);
        om.enableDefaultTyping(NON_FINAL);
    //        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    //        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public ChannelTopic expiredTopic() {
        return new ChannelTopic("__keyevent@0__:expired");  // 选择0号数据库
    }

    @Bean
    public ChannelTopic expiredTopic1() {
        return new ChannelTopic("__keyevent@1__:expired");  // 选择1号数据库
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(new KeyExpiredListener(redisMessageListenerContainer),expiredTopic());
        redisMessageListenerContainer.addMessageListener(new KeyExpiredListener(redisMessageListenerContainer),expiredTopic1());
        return redisMessageListenerContainer;
    }


//    当配置多个CacheManager的bean时，为防止冲突，得用@Primary修饰一个
    @Bean
    @Primary
    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
        System.out.println("==========redisTemplate.getValueSerializer() = " + redisTemplate.getValueSerializer());
        //指定缓存也用自定义的redis序列化，否则默认jdk序列化
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
        //30s有效期，注意！这里赋值后必须用redisCacheConfiguration再次接收，否则设置ttl无效
        redisCacheConfiguration =  redisCacheConfiguration.entryTtl(Duration.ofSeconds(30));
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
        return cacheManager;
    }

    @Bean(name = "myehcahe")
    public EhCacheCacheManager EhcacheManager() {
        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        return ehCacheManager;
    }



}
