package com.api.limit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 滑动时间窗口算法加载lua脚本
     * 滑动时间算法有一个问题就是在一定范围内，比如 60s 内只能有 10 个请求，当第一秒时就到达了 10 个请求，那么剩下的 59s 只能把所有的请求都给拒绝掉，而漏桶算法可以解决这个问题。
     */
    @Bean
    public RedisScript<Long> slideLimitRedisScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        // redisScript.setScriptText();  直接设置脚本字符串
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/redislua/slide-limit.lua")));
        return redisScript;
    }

    /**
     * 令牌算法加载lua脚本
     */
    @Bean
    public RedisScript<Long> tokenRedisScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/redislua/token-limit.lua")));
        return redisScript;
    }





}
