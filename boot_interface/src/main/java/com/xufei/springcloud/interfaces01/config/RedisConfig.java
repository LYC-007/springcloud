package com.xufei.springcloud.interfaces01.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    //自定义的redistemplate
    @Bean(name = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        //创建一个RedisTemplate对象，为了方便返回key为string，value为Object
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        //设置json序列化配置
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance);
        //string的序列化
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        //key采用string的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //value采用jackson的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hashkey采用string的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //hashvalue采用jackson的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     *
     * Spring Boot项目中使用RedisTemplate.delete()【此时使用原生的RedisTemplate来删除】</> 删除指定key失败 的解决办法
     *
     *
     * 注意到 Redis 的默认序列化机制 “ defaultSerializer ” ，如果没有自定义的序列化机制，则系统默认使用的是 “ org.springframework.data.redis.serializer.JdkSerializationRedisSerializer ” ，
     * 如果你在系统中自定义了 Redis 的序列化机制,此时就不能使用原生的 “ RedisTemplate redisTemplate; ” 而需要定义为泛型的 “ RedisTemplate <Object,Object> redisTemplate; ” 了，
     * 因为当我们再次新增的 key 的时候，使用的是 “ StringRedisSerializer ”序列化机制,但是在 delete 操作的时候是使用的是原生 API ，
     * 这样以来，即使你使用 hasKey 方法也会发现 redis 中存在这个 key ，但是实际 hasKey 返回 false，所以就会出现删除成功，但是实际的数据依然存在 Redis 服务器上咯。
     */
}
