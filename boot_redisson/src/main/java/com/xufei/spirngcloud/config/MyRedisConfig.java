package com.xufei.spirngcloud.config;



import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableConfigurationProperties(CacheProperties.class)//如果你想要在核心配置文件中的配置生效，你就的添加这个注解
//因为CacheProperties这个和配置文件绑定的类没有放到容器中，没有这个注解，在配置文件中写的配置项不会生效；
//1、EnableConfigurationProperties作用：1.开启配置绑定功能2、EnableConfigurationProperties把这个CacheProperties这个组件自动注册到容器中
@EnableCaching
@Configuration
public class MyRedisConfig extends CachingConfigurerSupport {
    //lettuce和jedis是操作redis的底层客户端，但是Spring把lettuce和jedis再次封装成redisTemplate;
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        //将当前对象的数据类型也存入序列化的结果字符串中
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //该方法是指定序列化输入的类型，就是将数据库里的数据按照一定类型存储到redis缓存中。
        //如果注释掉enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)，
        //那存储到redis里的数据将是没有类型的纯json，我们调用redis API获取到数据后，java解析将是一个LinkHashMap类型的key-value的数据结构，我们需要使用的话就要自行解析，这样增加了编程的复杂度。
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); 但是这个方法过时了，现在使用下面这个
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance , ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);

        // 如果有用到LocalDataTime的话解决jackson2无法反序列化LocalDateTime的问题
        objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        Jackson2JsonRedisSerializer<Object>  jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<> (Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        //key序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //value hashmap序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory, CacheProperties cacheProperties) {
        Jackson2JsonRedisSerializer<Object>  jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<> (Object.class);
        //解决查询缓存转换异常的问题
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //该方法是指定序列化输入的类型，就是将数据库里的数据按照一定类型存储到redis缓存中。
        //如果注释掉enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)，
        //那存储到redis里的数据将是没有类型的纯json，我们调用redis API获取到数据后，java解析将是一个LinkHashMap类型的key-value的数据结构，我们需要使用的话就要自行解析，这样增加了编程的复杂度。
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); 但是这个方法过时了，现在使用下面这个
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance , ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // 配置序列化（解决乱码的问题）,过期时间600秒
                .entryTtl(Duration.ofSeconds(600))
                //配置key的序列化机制
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                //配置Value的序列化机制
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();//获取到在核心配置文件中配置的东西
        if (redisProperties.getTimeToLive() != null) {  //表示在核心配置文件没有配置就用上面配置的过期时间
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config=config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            //如果核心配置文件没有指定，就用这里的---禁止缓存 null 值
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(config)
                .build();
    }



}

