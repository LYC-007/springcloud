package com.xufei.springcloud.session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 父域名:gulimall.com
 * auth.gulimall.com        sdfdfsd.gulimall.com  都是它的子域名
 */


@Configuration
public class MySpringSessionConfig {

    //@Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer cookieSerializer=new DefaultCookieSerializer();
        //SpringSession解决子域共享问题
        //放大作用域，指定域名为父域名
        cookieSerializer.setDomainName("gulimall.com");
        cookieSerializer.setCookieName("ACHANGSESSION");
        return cookieSerializer;
    }
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){//设置序列化机制
        return new GenericJackson2JsonRedisSerializer();
    }
}
